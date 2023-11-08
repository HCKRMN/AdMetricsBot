package com.kuzmichev.AdMetricsBot.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsController {

    final YclientsRepository yclientsRepository;
    final UserRepository userRepository;
    final TempDataRepository tempDataRepository;
    final MessageWithoutReturn messageWithoutReturn;
    final CloseButtonKeyboard closeButtonKeyboard;
    final MessageManagementService messageManagementService;
    final YclientsTestKeyboard yclientsTestKeyboard;

    @Value("${bearer-authentication}")
    String bearerAuthentication;
    @Value("${yclientsApplicationId}")
    String applicationId;
    @Value("${yclientsPartnerCallbackURL}")
    String yclientsPartnerCallbackURL;

    @RequestMapping(value = "/yclients")
    public String getYclientsToken(@RequestParam(name = "salon_id") String salonId,
                                   @RequestParam(name = "user_data") String userData,
                                   @RequestParam(name = "user_data_sign") String userDataSign) {

        // Декодирование данных пользователя
        String decodedUserData = new String(Base64.getDecoder().decode(userData), StandardCharsets.UTF_8);

        // Извлечение необходимых данных из JSON
        JSONObject json = new JSONObject(decodedUserData);
        long phoneNumber = json.getLong("phone");
        String salonName = json.getString("salon_name");

        // Поиск пользователя по номеру телефона
        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user != null) {
            String chatId = user.getChatId();
            String userState = user.getUserState();

            // Получение временных данных пользователя
            TempData tempData = tempDataRepository.getByChatId(chatId);
            String projectId = tempData.getLastProjectId();

            if (projectId == null) {
                // Отправка сообщения, если проект не найден
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.YCLIENTS_NON_PROJECTS_MESSAGE.getMessage(),
                        closeButtonKeyboard.closeButtonKeyboard());
                return "yclientsError";
            }

            // Проверка подписи данных пользователя
            String computedSign = new HmacUtils("HmacSHA256", bearerAuthentication).hmacHex(decodedUserData);
            if (!computedSign.equals(userDataSign)) {
                // Отправка сообщения об ошибке, если подпись не совпадает
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.YCLIENTS_ERROR_DECODE_MESSAGE.getMessage(),
                        closeButtonKeyboard.closeButtonKeyboard());
                return "yclientsError";
            }

            // Создание HTTP-запроса
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + bearerAuthentication);
            headers.add("Accept", "application/vnd.yclients.v2+json");

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("salon_id", salonId);
            requestBody.add("application_id", applicationId);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Удаление предыдущих сообщений и отправка нового
            int messageId = tempData.getLastMessageId();
            messageManagementService.putMessageToQueue(chatId, messageId+3);
            messageManagementService.deleteMessage(chatId);

            try {
                // Отправка HTTP-запроса и обработка успешного ответа
                restTemplate.exchange(yclientsPartnerCallbackURL, HttpMethod.POST, requestEntity, String.class);
                Yclients yclients = Yclients.builder()
                        .projectId(projectId)
                        .chatId(chatId)
                        .salonId(salonId)
                        .salonName(salonName)
                        .build();
                yclientsRepository.save(yclients);

                // Отправка сообщения в зависимости от состояния пользователя
                if (StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName().equals(userState)) {
                    messageWithoutReturn.sendMessage(
                            chatId,
                            MessageEnum.REGISTRATION_TEST_INPUTS_MESSAGE.getMessage(),
                            yclientsTestKeyboard.yclientsTestMenu(userState));
                } else {
                    messageWithoutReturn.sendMessage(
                            chatId,
                            MessageEnum.INPUT_TEST_MESSAGE.getMessage(),
                            yclientsTestKeyboard.yclientsTestMenu(userState));
                }

                log.info("Пользователь {} добавил Yclients", chatId);
                return "yclients";

            } catch (HttpClientErrorException e) {
                // Обработка ошибок HTTP
                HttpStatus statusCode = (HttpStatus) e.getStatusCode();

                if (statusCode == HttpStatus.FORBIDDEN) {
                    // Обработка ошибки 403 Forbidden
                    String message = extractMessageFromErrorResponse(e);
                    log.error("Пользователь {}. Произошла ошибка при подключении Yclients: {}", chatId, message);
                    messageWithoutReturn.sendMessage(
                            chatId,
                            "Произошла ошибка" + message,
                            closeButtonKeyboard.closeButtonKeyboard());
                } else {
                    // Обработка других HTTP ошибок
                    log.error("Пользователь {}. Произошла ошибка при подключении Yclients: {}", chatId, e.getMessage());
                }

                return "yclientsError";

            } catch (RestClientException e) {
                // Обработка других ошибок, не связанных с HTTP
                log.error("Пользователь {}. Произошла ошибка при подключении Yclients: {}", chatId, e.getMessage());
                return "yclientsError";
            }
        }

        // Пользователь не найден
        log.error("Пользователь с номером {} не найден. {}", phoneNumber, decodedUserData);
        return "yclientsUserNotFound";
    }

    private String extractMessageFromErrorResponse(HttpClientErrorException e) {
        try {
            JsonObject jsonObject = JsonParser.parseString(e.getResponseBodyAsString()).getAsJsonObject();
            JsonObject metaObject = jsonObject.getAsJsonObject("meta");
            if (metaObject != null && metaObject.has("message")) {
                return ": " + metaObject.get("message").getAsString();
            }
        } catch (Exception ex) {
            log.error("Ошибка при извлечении сообщения из ответа: {}", ex.getMessage());
        }
        return "";
    }
}

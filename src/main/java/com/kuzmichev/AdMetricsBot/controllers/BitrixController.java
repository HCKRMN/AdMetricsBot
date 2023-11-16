package com.kuzmichev.AdMetricsBot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BitrixTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Optional;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixController {
    final BitrixRepository bitrixRepository;
    final MessageManagementService messageManagementService;
    final MessageWithoutReturn messageWithoutReturn;
    final BitrixTestKeyboard bitrixTestKeyboard;
    @Value("${bitrixClientID}")
    String clientId;
    @Value("${bitrixSecret}")
    String clientSecret;
    @Value("${bitrixOauthURL}")
    String tokenUrl;

    @RequestMapping(value = "/bitrix")
    public String getBitrixToken(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state,
                                 @RequestParam(name = "member_id") String memberId) {


        String[] parts = state.split(":", 3);

        if (parts.length == 3) {
            String chatId = parts[0];
            String projectId = parts[1];
            String userState = parts[2];

            // Второй шаг OAuth-авторизации
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "authorization_code");
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            requestBody.add("code", code);


            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            try {
                ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);


                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(responseBody);
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }

                String accessToken = jsonNode.get("access_token").asText();
                String refreshToken = jsonNode.get("refresh_token").asText();
                String userId = jsonNode.get("user_id").asText();

                Optional<Bitrix> bitrixOptional = bitrixRepository.findByProjectId(projectId);
                if (bitrixOptional.isPresent()) {
                    Bitrix bitrix = bitrixOptional.get();
                    bitrix.setCode(code);
                    bitrix.setMemberId(memberId);
                    bitrix.setAccessToken(accessToken);
                    bitrix.setRefreshToken(refreshToken);
                    bitrix.setUserId(userId);
                    bitrixRepository.save(bitrix);
                }

                messageManagementService.deleteMessage(chatId);

                if (userState.contains(StateEnum.REGISTRATION.getStateName())) {
                    messageWithoutReturn.sendMessage(
                            chatId,
                            MessageEnum.REGISTRATION_TEST_INPUTS_MESSAGE.getMessage(),
                            bitrixTestKeyboard.bitrixTestMenu(userState));

                } else {
                    messageWithoutReturn.sendMessage(
                            chatId,
                            MessageEnum.INPUT_TEST_MESSAGE.getMessage(),
                            bitrixTestKeyboard.bitrixTestMenu(userState));
                }
                log.info("Пользователь {} добавил аккаунт Bitrix", chatId);

            } catch (HttpClientErrorException e) {
                // Обработка ошибки HttpClientErrorException
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    // Обработка ошибки 401 Unauthorized
                    log.warn("Ошибка аутентификации Битрикс: " + e.getMessage());
                } else {
                    // Обработка других HTTP ошибок
                    log.warn("HTTP ошибка в Битрикс: " + e.getMessage());
                }
                return "bitrixError";
            } catch (RestClientException e) {
                // Обработка других ошибок RestClientException
                log.warn("Ошибка клиента RestTemplate в Битрикс: " + e.getMessage());
                return "bitrixError";
            } catch (Exception e) {
                // Обработка всех других исключений
                log.warn("Неизвестная ошибка в Битрикс: " + e.getMessage());
                return "bitrixError";
            }
        }
        return "bitrix";
    }
}

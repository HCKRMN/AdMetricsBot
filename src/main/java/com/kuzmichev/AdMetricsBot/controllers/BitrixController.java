package com.kuzmichev.AdMetricsBot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.model.*;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixController {
    final BitrixRepository bitrixRepository;
    final TempDataRepository tempDataRepository;
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

//        // Второй шаг OAuth-авторизации
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        System.out.println("Request body: " + requestBody);


        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);

        String responseBody = response.getBody();
        System.out.println("Response from Bitrix server: " + responseBody);
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

        TempData tempData = tempDataRepository.getByChatId(chatId);

        int messageId = tempData.getLastMessageId();
        messageManagementService.putMessageToQueue(chatId, messageId);
        messageManagementService.deleteMessage(chatId);

        if (userState.equals(RegistrationStateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
            messageWithoutReturn.sendMessage(
                    chatId,
                    RegistrationMessageEnum.REGISTRATION_TEST_INPUTS_MESSAGE.getMessage(),
                    bitrixTestKeyboard.bitrixTestMenu(userState));

        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    SettingsMessageEnum.INPUT_TEST_MESSAGE.getMessage(),
                    bitrixTestKeyboard.bitrixTestMenu(userState));
        }
        log.info("Пользователь {} добавил аккаунт Bitrix", chatId);


        // тут надо написать код вывода сообщения об успехе


        }


        return "bitrix";
    }
}
















//@Controller
//@Slf4j
//public class BitrixController {
//
//
//    @PostMapping(value = "/bitrix/callback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public String handleBitrixCallback(@RequestBody MultiValueMap<String, String> formData) {
//        System.out.println("Received Bitrix callback POST request:");
//
//        // Print each parameter in the request
//        formData.forEach((key, values) -> {
//            System.out.println(key + ": " + String.join(", ", values));
//        });
////    }




//    @RequestMapping(value = "/bitrix")
//    public String getBitrixToken(@RequestParam(name = "code", required = false) String code) {
//
//        System.out.println(code + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//        return "bitrix";
//    }
//}
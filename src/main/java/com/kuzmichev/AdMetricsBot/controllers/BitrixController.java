package com.kuzmichev.AdMetricsBot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuzmichev.AdMetricsBot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@Slf4j
public class BitrixController {
    private final BitrixRepository bitrixRepository;

    @Value("${bitrixClientID}")
    String clientId;
    @Value("${bitrixSecret}")
    String clientSecret;

    public BitrixController(BitrixRepository bitrixRepository) {
        this.bitrixRepository = bitrixRepository;
    }

    @RequestMapping(value = "/bitrix")
    public String getBitrixToken(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state,
                                 @RequestParam(name = "member_id") String memberId) {

        // Второй шаг OAuth-авторизации
        String tokenUrl = "https://oauth.bitrix.info/oauth/token/";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);

        String responseBody = response.getBody();
        System.out.println("Response from Bitrix server: " + responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String accessToken = jsonNode.get("access_token").asText();
        String refreshToken = jsonNode.get("refresh_token").asText();
        String userId = jsonNode.get("user_id").asText();

        Optional<Bitrix> bitrixOptional = bitrixRepository.findByProjectId(state);
        if (bitrixOptional.isPresent()) {
            Bitrix bitrix = bitrixOptional.get();
            bitrix.setCode(code);
            bitrix.setMemberId(memberId);
            bitrix.setAccessToken(accessToken);
            bitrix.setRefreshToken(refreshToken);
            bitrix.setUserId(userId);
            bitrixRepository.save(bitrix);
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
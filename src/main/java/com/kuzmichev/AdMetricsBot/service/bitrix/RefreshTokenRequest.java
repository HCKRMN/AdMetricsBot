package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RefreshTokenRequest {
    final BitrixRepository bitrixRepository;
    @Value("${bitrixClientID}")
    String clientId;
    @Value("${bitrixSecret}")
    String clientSecret;
    public String refreshTokenRequest(Bitrix bitrix){

        String refreshToken = bitrix.getRefreshToken();
        String projectId = bitrix.getProjectId();

        String tokenUrl = "https://oauth.bitrix.info/oauth/token/";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
            log.info("User " + bitrix.getChatId() + ". Токен bitrix обновлен");
        } catch (JsonProcessingException e) {
            log.error("User " + bitrix.getChatId() + ". Ошибка при обновлении токена bitrix" + e);
            throw new RuntimeException(e);
        }

        String accessToken = jsonNode.get("access_token").asText();

        Optional<Bitrix> bitrixOptional = bitrixRepository.findByProjectId(projectId);
        if (bitrixOptional.isPresent()) {
            Bitrix bitrixSave = bitrixOptional.get();
            bitrixSave.setAccessToken(accessToken);
            bitrixRepository.save(bitrixSave);
        }
        return accessToken;
    }
}

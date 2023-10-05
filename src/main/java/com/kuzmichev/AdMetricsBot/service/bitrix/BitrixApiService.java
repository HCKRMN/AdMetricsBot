package com.kuzmichev.AdMetricsBot.service.bitrix;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixApiService {
    public JSONObject makeBitrixApiRequest(String url, String chatId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new JSONObject(responseEntity.getBody());
        } else {
            log.error("User: " + chatId + " Ошибка при получении типа срм bitrix: json == null " + responseEntity.getStatusCode());
            return null;
        }
    }
}


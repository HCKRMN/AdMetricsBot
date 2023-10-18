package com.kuzmichev.AdMetricsBot.telegram;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SetWebHookURL {
    @Value("${telegram.api-url}")
    String link;
    @Value("${telegram.bot-token}")
    String token;
    @Value("${telegram.webhook-path}")
    String path;

    @PostConstruct
    public void updateURL(){
        String url = link + "bot" + token + "/setWebhook?url=" + path;
        RestTemplate restTemplate = new RestTemplate();
        JsonNode responseNode = restTemplate.postForObject(url, null, JsonNode.class);
        if (responseNode != null && responseNode.has("description")) {
            String description = responseNode.get("description").asText();
            log.info("Telegram API: {}", description);
        }
    }
}

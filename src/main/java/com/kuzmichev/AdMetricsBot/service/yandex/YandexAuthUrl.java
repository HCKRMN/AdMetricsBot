package com.kuzmichev.AdMetricsBot.service.yandex;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexAuthUrl {
    @Value("${yandexClientID}")
    String clientId;
    @Value("${telegram.webhook-path}")
    String link;
    @Value("${yandexRedirectURL}")
    String redirectUri;

    public String getYandexAuthorizationUrl(String chatId, String userState) {
        return "https://oauth.yandex.ru/authorize" +
                "?response_type=token" +
                "&client_id=" + clientId +
                "&redirect_uri=" + link + redirectUri +
                "&state=" + chatId +
                "_" + userState;
    }
//     Возможно понадобится включить апи в настройках яндекса
//    public String getApiSettingsUrl(String chatId) {
//        return "https://direct.yandex.ru/registered/main.pl?cmd=apiSettings";
//    }
}
package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.service.YandexMainRequest;
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
public class AddYandex {
    final YandexMainRequest yandexMainRequest;
    @Value("${yandexClientID}")
    String clientId;
    @Value("${telegram.webhook-path}")
    String link;
    @Value("${yandexRedirectURI}")
    String redirectUri;

    public String getYandexAuthorizationUrl(String chatId, String userState) {
        return "https://oauth.yandex.ru/authorize" +
                "?response_type=token" +
                "&client_id=" + clientId +
                "&redirect_uri=" + link + redirectUri +
                "&state=" + chatId +
                "_" + userState;
    }
//    public String getApiSettingsUrl(String chatId) {
//        return "https://direct.yandex.ru/registered/main.pl?cmd=apiSettings";
//    }

    public String testYandex(String chatId) {
        try {
            int result = yandexMainRequest.yandexMainRequest(chatId);
            System.out.println(result);
            if (result == -1) {
                return SettingsMessageEnum.YANDEX_ERROR_GET_RESULT_MESSAGE.getMessage();
            } else {
                return SettingsMessageEnum.YANDEX_RESULT_MESSAGE.getMessage() + result;
            }
        } catch (Exception e) {
            log.error(e.toString());
            return SettingsMessageEnum.YANDEX_ERROR_GET_TOKEN_MESSAGE.getMessage();
        }
    }
}
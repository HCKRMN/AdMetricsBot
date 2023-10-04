package com.kuzmichev.AdMetricsBot.service.yandex;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexTestMessage {
    YandexMainRequest yandexMainRequest;

    public String getYandexTestMessage(String chatId) {
        try {
            int result = yandexMainRequest.yandexMainRequest(chatId);
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
package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.service.yandex.YandexAuthUrl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexAddKeyboard implements InlineKeyboard {
    YandexAuthUrl yandexAuthUrl;
    BackAndExitKeyboard backAndExitKeyboard;

    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.YANDEX_ADD_TOKEN_LINK_BUTTON.getButtonName())
                                .url(yandexAuthUrl.getYandexAuthorizationUrl(chatId, userState))
//                                .build(),
//                                        на всякий случай оставлю кнопку получения доступа к апи, вроде как ненужно, но пусть будет
//                        InlineKeyboardButton.builder()
//                                .text(ButtonEnum.YANDEX_API_SETTINGS_BUTTON.getButtonName())
//                                .url(addYandex.getApiSettingsUrl(chatId))
                        .build()))
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}

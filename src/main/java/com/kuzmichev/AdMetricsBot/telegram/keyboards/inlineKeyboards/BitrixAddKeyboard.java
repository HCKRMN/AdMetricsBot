package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixAuthUrl;
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
public class BitrixAddKeyboard {
    BitrixAuthUrl bitrixAuthUrl;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup bitrixLinkMenu(String chatId, String projectId, String userState) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.LINK_BUTTON.getButtonName())
                                .url(bitrixAuthUrl.getBitrixAuthorizationUrl(chatId, projectId, userState))
                        .build()))
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}

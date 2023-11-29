package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeZoneKeyboard implements InlineKeyboard {
    final BackAndExitKeyboard backAndExitKeyboard;
    @Value("${telegram.webhook-path}")
    String link;
    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.LINK_BUTTON.getButtonName())
                        .url(ipToTimeZoneLink)
                        .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.MANUAL_INPUT_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}

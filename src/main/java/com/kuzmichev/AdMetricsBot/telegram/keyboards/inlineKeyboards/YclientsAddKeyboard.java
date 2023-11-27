package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
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
public class YclientsAddKeyboard implements InlineKeyboard {
    final BackAndExitKeyboard backAndExitKeyboard;

    @Value("${yclientsAppstoreURL}")
    String yclientsAppstoreURL;

    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.LINK_BUTTON.getButtonName())
                                .url(yclientsAppstoreURL)
                                .build()))
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}

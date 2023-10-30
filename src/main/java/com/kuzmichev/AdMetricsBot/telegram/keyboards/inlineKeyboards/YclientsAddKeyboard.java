package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsAddKeyboard {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final BackAndExitKeyboard backAndExitKeyboard;

    @Value("${yclientsAppstoreURL}")
    String yclientsAppstoreURL;

    public InlineKeyboardMarkup addYclientsMenu(String chatId, String userState) {
        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        yclientsAppstoreURL
                                )
                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

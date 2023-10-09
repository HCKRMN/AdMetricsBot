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

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeZoneKeyboard {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final BackAndExitKeyboard backAndExitKeyboard;
    @Value("${telegram.webhook-path}")
    String link;
    public InlineKeyboardMarkup timeZoneKeyboard(String chatId, String userState) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        // Добавить временную зону по ссылке
                                        ButtonEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        ipToTimeZoneLink
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        // Ручной ввод текущего времени пользователя
                                        ButtonEnum.MANUAL_INPUT_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName(),
                                        null
                                )
                        ), backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

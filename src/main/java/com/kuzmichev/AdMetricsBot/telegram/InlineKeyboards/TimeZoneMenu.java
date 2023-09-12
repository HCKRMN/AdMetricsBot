package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
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
public class TimeZoneMenu {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final BackAndExitMenu backAndExitMenu;
    @Value("${telegram.webhook-path}")
    String link;
    public InlineKeyboardMarkup timeZoneMenu(String chatId) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        // Добавить временную зону по ссылке
                                        ButtonNameEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        ipToTimeZoneLink
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        // Ручной ввод текущего времени пользователя
                                        ButtonNameEnum.MANUAL_INPUT_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        backAndExitMenu.backAndExitMenuButtons(chatId)
                )
        );
    }
}

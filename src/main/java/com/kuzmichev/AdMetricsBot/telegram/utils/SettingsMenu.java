package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SettingsMenu {
    InlineKeyboards inlineKeyboards;
    UserStateEditor userStateEditor;

    /**
     * Создает сообщение с настройками меню.
     *
     * @param chatId идентификатор чата
     * @return сообщение с настройками меню
     */
    public SendMessage SettingsMenuMaker(String chatId) {
        userStateEditor.editUserState(chatId, UserStateEnum.SETTINGS_EDIT_STATE);
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboards.settingsMenu(chatId));
        return sendMessage;
    }
}

package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageEditor {
    UserStateEditor userStateEditor;

    public EditMessageText editMessage(String chatId, int messageId, BotMessageEnum text, InlineKeyboardMarkup keyboard) {

        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(text.getMessage());
        newMessage.setReplyMarkup(keyboard);
        return newMessage;
    }

    public EditMessageText editMessage(String chatId, int messageId, BotMessageEnum text, InlineKeyboardMarkup keyboard, UserStateEnum userState) {

    EditMessageText newMessage = new EditMessageText();
                newMessage.setChatId(chatId);
                newMessage.setMessageId(messageId);
                newMessage.setText(text.getMessage());
                newMessage.setReplyMarkup(keyboard);
                userStateEditor.editUserState(chatId, userState);
                return newMessage;
    }

    public DeleteMessage deleteMessage(String chatId, int messageId, UserStateEnum userState) {

        DeleteMessage settingsExit = new DeleteMessage();
        settingsExit.setChatId(chatId);
        settingsExit.setMessageId(messageId);
        userStateEditor.editUserState(chatId, userState);
        return settingsExit;

    }

}

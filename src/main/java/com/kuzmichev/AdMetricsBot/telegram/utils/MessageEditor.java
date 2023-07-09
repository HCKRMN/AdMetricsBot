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

    public EditMessageText editMessage(
            String chatId,
            int messageId,
            BotMessageEnum text,
            InlineKeyboardMarkup keyboard,
            UserStateEnum userState) {

        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(text.getMessage());
        if (keyboard != null) {
            newMessage.setReplyMarkup(keyboard);
        }
        if (userState != null) {
            userStateEditor.editUserState(chatId, userState);
        }
        return newMessage;
    }

    public DeleteMessage deleteMessage(
            String chatId,
            int messageId,
            UserStateEnum userState) {

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        if (userState != null) {
            userStateEditor.editUserState(chatId, userState);
        }
        return deleteMessage;

    }

}

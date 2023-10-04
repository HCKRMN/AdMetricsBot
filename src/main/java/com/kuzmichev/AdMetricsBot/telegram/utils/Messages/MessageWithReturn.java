package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import com.kuzmichev.AdMetricsBot.telegram.utils.UserStateEditor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageWithReturn {
    UserStateEditor userStateEditor;

    public SendMessage sendMessage(
            String chatId,
            String text,
            InlineKeyboardMarkup keyboard,
            String state) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        if (keyboard != null) {
            sendMessage.setReplyMarkup(keyboard);
        }
        if (state != null) {
            userStateEditor.editState(chatId, state);
        }
        return sendMessage;
    }

    public EditMessageText editMessage(
            String chatId,
            int messageId,
            String text,
            String state,
            InlineKeyboardMarkup keyboard) {

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(text);
        if (state != null) {
            userStateEditor.editState(chatId, state);
        }
        if (keyboard != null) {
            editMessage.setReplyMarkup(keyboard);
        }
        return editMessage;
    }

    public DeleteMessage deleteMessage(
            String chatId,
            int messageId,
            String state) {

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        if (state != null) {
            userStateEditor.editState(chatId, state);
        }
        return deleteMessage;
    }

}

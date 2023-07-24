package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
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
            UserStateEnum userState) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        if (keyboard != null) {
            sendMessage.setReplyMarkup(keyboard);
        }
        if (userState != null) {
            userStateEditor.editUserState(chatId, userState);
        }
        return sendMessage;
    }

    public EditMessageText editMessage(
            String chatId,
            int messageId,
            String text,
            UserStateEnum userState,
            InlineKeyboardMarkup keyboard) {

        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(chatId);
        newMessage.setMessageId(messageId);
        newMessage.setText(text);
        if (userState != null) {
            userStateEditor.editUserState(chatId, userState);
        }
        if (keyboard != null) {
            newMessage.setReplyMarkup(keyboard);
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

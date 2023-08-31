package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.utils.UserStateEditor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageWithoutReturn {
    ApplicationEventPublisher eventPublisher;
    UserStateEditor userStateEditor;

    public void sendMessage(SendMessage sendMessage) {
        eventPublisher.publishEvent(sendMessage);
    }
    public void editMessage(EditMessageText sendMessage) { eventPublisher.publishEvent(sendMessage);
    }

    public void editMessage(
            String chatId,
            int messageId,
            String text,
            UserStateEnum userState,
            InlineKeyboardMarkup keyboard) {

        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText(text);
        if (userState != null) {
            userStateEditor.editUserState(chatId, userState);
        }
        if (keyboard != null) {
            message.setReplyMarkup(keyboard);
        }
        eventPublisher.publishEvent(message);
    }

    public void sendMessage(String chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        eventPublisher.publishEvent(message);
    }
    public void sendMessage(String chatId, String textToSend, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(keyboard);
        eventPublisher.publishEvent(message);
    }

    public void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        eventPublisher.publishEvent(deleteMessage);
    }
}
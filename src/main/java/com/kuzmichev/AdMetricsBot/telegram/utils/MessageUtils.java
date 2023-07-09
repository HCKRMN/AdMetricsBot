package com.kuzmichev.AdMetricsBot.telegram.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageUtils {
    ApplicationEventPublisher eventPublisher;

    public void sendMessage(SendMessage sendMessage) {
        eventPublisher.publishEvent(sendMessage);
    }
    public void editMessage(EditMessageText sendMessage) {
        eventPublisher.publishEvent(sendMessage);
    }

    public void sendMessage(String chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        eventPublisher.publishEvent(message);
    }

    public void deleteMessage(String chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        System.out.println(deleteMessage + " deleteMessage");
        eventPublisher.publishEvent(deleteMessage);
    }
}
package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import com.kuzmichev.AdMetricsBot.telegram.AdMetricsBot;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageEventHandler {
    AdMetricsBot adMetricsBot;
    TempDataSaver tempDataSaver;

    @EventListener
    public void handleSendMessageEvent(SendMessage message) {
        try {
            int messageId = adMetricsBot.execute(message).getMessageId();
            String chatId = message.getChatId();
            tempDataSaver.tempLastMessageId(chatId, messageId);
        } catch (TelegramApiException e) {
            log.error("ERROR_TEXT" + e.getMessage());
        }
    }

    @EventListener
    public void handleDeleteMessageEvent(DeleteMessage message) {
        try {
            adMetricsBot.execute(message);
        } catch (TelegramApiException e) {
            log.error("ERROR_TEXT" + e.getMessage());
        }
    }
}

package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NotificationController {
    ScheduledMessageRepository scheduledMessageRepository;

    // Включает уведомления
    public void enableNotifications(String chatId) {
        Optional<ScheduledMessage> scheduledMessageOptional = scheduledMessageRepository.findByChatId(chatId);
        if (scheduledMessageOptional.isPresent()) {
            ScheduledMessage scheduledMessage = scheduledMessageOptional.get();
            scheduledMessage.setEnableSendingMessages(true);
            scheduledMessageRepository.save(scheduledMessage);
        }
    }

    // Выключает уведомления
    public void disableNotifications(String chatId) {
        Optional<ScheduledMessage> scheduledMessageOptional = scheduledMessageRepository.findByChatId(chatId);
        if (scheduledMessageOptional.isPresent()) {
            ScheduledMessage scheduledMessage = scheduledMessageOptional.get();
            scheduledMessage.setEnableSendingMessages(false);
            scheduledMessageRepository.save(scheduledMessage);
        }
    }

}

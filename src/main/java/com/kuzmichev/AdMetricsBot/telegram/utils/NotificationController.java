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

    public void enableNotifications(String chatId) {
        Optional<ScheduledMessage> scheduledMessageOptional = scheduledMessageRepository.findByChatId(chatId);
        ScheduledMessage scheduledMessage = scheduledMessageOptional.orElseGet(ScheduledMessage::new);
        scheduledMessage.setEnableSendingMessages(true);
        scheduledMessageRepository.save(scheduledMessage);

    }

    public void disableNotifications(String chatId) {
        Optional<ScheduledMessage> scheduledMessageOptional = scheduledMessageRepository.findByChatId(chatId);
        ScheduledMessage scheduledMessage = scheduledMessageOptional.orElseGet(ScheduledMessage::new);
        scheduledMessage.setEnableSendingMessages(false);
        scheduledMessageRepository.save(scheduledMessage);
    }

}

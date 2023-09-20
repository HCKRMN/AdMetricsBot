package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeZoneDefinition {
    ScheduledMessageRepository scheduledMessageRepository;

    public void manualTimeZone(String chatId, String messageText) {
        LocalTime timerMessage = LocalTime.parse(messageText.replace(" ", ":"));
        LocalTime timeZone = LocalTime.now();

        Duration duration = Duration.between(timerMessage, timeZone);
        LocalTime resultTime = timeZone.plusSeconds(duration.getSeconds());

        ScheduledMessage scheduledMessage = new ScheduledMessage();
        scheduledMessage.setChatId(chatId);
        scheduledMessage.setTimerMessage(resultTime);
        scheduledMessageRepository.save(scheduledMessage);
    }
}

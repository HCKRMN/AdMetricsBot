package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.kuzmichev.AdMetricsBot.telegram.AdMetricsBot;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageSender {
    ScheduledMessageRepository scheduledMessageRepository;
    YaRepository yaRepository;
    AdMetricsBot telegramBot;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            List<ScheduledMessage> scheduledMessages = scheduledMessageRepository.findByTime(now);
            for (ScheduledMessage scheduledMessage : scheduledMessages) {
                try {
                    telegramBot.sendMessage(scheduledMessage.getChatId(), "Затраты на рекламу в Яндекс директ: " + YandexDirectRequest.ya(yaRepository, scheduledMessage.getChatId()));

                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}

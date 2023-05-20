package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.kuzmichev.AdMetricsBot.telegram.AdMetricsBot;

@Slf4j
@Service
public class MessageSender {
    private final ScheduledMessageRepository scheduledMessageRepository;
    private final YaRepository yaRepository;
    private final AdMetricsBot telegramBot;

    @Autowired
    public MessageSender(ScheduledMessageRepository scheduledMessageRepository,
                         YaRepository yaRepository,
                         AdMetricsBot telegramBot) {
        this.scheduledMessageRepository = scheduledMessageRepository;
        this.yaRepository = yaRepository;
        this.telegramBot = telegramBot;
    }
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalTime now = LocalTime.now();
            String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
            System.out.println(formattedTime);
//            List<ScheduledMessage> scheduledMessages = scheduledMessageRepository.findByTime(formattedTime);

            List<ScheduledMessage> scheduledMessages = scheduledMessageRepository.findByTime(now);
            System.out.println(scheduledMessages);

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

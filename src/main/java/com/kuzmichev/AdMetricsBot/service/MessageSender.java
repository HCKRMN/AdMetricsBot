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
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    //Запускаем на старте
    @PostConstruct
    public void startScheduler() {
        scheduler.schedule(this::sendMessageTask, 0, TimeUnit.MINUTES);
    }

    // Метод, выполняющий задачу отправки сообщений
    private void sendMessageTask() {
        // Округляем текущее время до минуты
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        System.out.println("Текущее время: " + now);

        // Получаем список запланированных сообщений для текущего времени
        List<ScheduledMessage> scheduledMessages = scheduledMessageRepository.findByTime(now);
        System.out.println("Запланированные сообщения: " + scheduledMessages);

        // Отправляем сообщения
        for (ScheduledMessage scheduledMessage : scheduledMessages) {
            try {
                telegramBot.sendMessage(scheduledMessage.getChatId(), "Затраты на рекламу в Яндекс директ: " + YandexDirectRequest.ya(yaRepository, scheduledMessage.getChatId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Вычисляем новую задержку до следующей отправки сообщений
        long delayMinutes = scheduledMessageRepository.findMinTimerMessage().toSecondOfDay() / 60 - LocalTime.now().toSecondOfDay() / 60;
        System.out.println("Отправка следующих сообщений через: " + delayMinutes + " минут");

        // Запускаем задачу отправки сообщений с новой задержкой
        scheduler.schedule(this::sendMessageTask, delayMinutes, TimeUnit.MINUTES);
    }
}

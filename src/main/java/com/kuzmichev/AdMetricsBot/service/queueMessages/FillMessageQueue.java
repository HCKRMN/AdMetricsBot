package com.kuzmichev.AdMetricsBot.service.queueMessages;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.service.queueMessages.Cache.LocalCacheQueue;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
// Сервис для заполнения очереди сообщений в кеше
public class FillMessageQueue {
    ScheduledMessageRepository scheduledMessageRepository;  // Репозиторий для работы с сообщениями
    LocalCacheQueue localCacheQueue;  // Кеш очереди сообщений
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);  // Планировщик задач

    @PostConstruct
    public void startScheduler() {
        // Получаем текущую минуту
        int currentMinute = LocalTime.now().getMinute();

        // Вычисляем время до следующей cacheMinute минуты
        int cacheMinute = 53;
        int delayMinutes = (currentMinute >= cacheMinute) ? (60 - currentMinute + cacheMinute) : (cacheMinute - currentMinute);

        scheduler.scheduleAtFixedRate(() -> {
            // Получаем текущее время + 1 час
            LocalTime nextHour = LocalTime.now().plusHours(1);
            int hour = nextHour.getHour();

            // Получаем сообщения из базы данных, относящиеся к текущему часу
            List<ScheduledMessage> messages = scheduledMessageRepository.findByTimerMessageHours(hour);

            // Сортировка списка по возрастанию поля timerMessage
            Collections.sort(messages, Comparator.comparing(ScheduledMessage::getTimerMessage));

            // Наполняем кеш LocalCacheQueue
            for (ScheduledMessage message : messages) {
                localCacheQueue.setUserTime(message.getChatId(), message.getTimerMessage());
            }

            log.info("Кеш наполнен: " + localCacheQueue.getAllUserTime());

        }, delayMinutes, 60, TimeUnit.MINUTES);
    }
}

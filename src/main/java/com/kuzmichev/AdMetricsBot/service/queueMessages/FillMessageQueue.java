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
// Эта штука регулярно в 55 минут каждого часа наполняет очередь кеша, из которой котом будет вытаскиваться по очереди сообщения
public class FillMessageQueue {
    ScheduledMessageRepository scheduledMessageRepository;
    LocalCacheQueue localCacheQueue;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startScheduler() {
        // Получаем текущую минуту
        int currentMinute = LocalTime.now().getMinute();
        // Вычисляем время до следующей cacheMinute минуты
        int cacheMinute = 33;
        int delayMinutes = (currentMinute >= cacheMinute) ? (60 - currentMinute + cacheMinute) : (cacheMinute - currentMinute);

//        scheduler.scheduleAtFixedRate(() -> {
//            System.out.println(1);
//            LocalTime now = LocalTime.now();
//            LocalTime nextHour = now.plusHours(1);
//            System.out.println(2);
//            List<ScheduledMessage> messages = scheduledMessageRepository.findByTimerMessageHour(nextHour);
//            System.out.println(3);
//            System.out.println(messages);
//            // Отсортировать список messages по возрастанию timerMessage
//            messages.sort(Comparator.comparing(ScheduledMessage::getTimerMessage));
//            System.out.println(messages);
//            System.out.println(4);
//            // Заполнить LocalCacheQueue
//            for (ScheduledMessage message : messages) {
//                localCacheQueue.setUserTime(message.getChatId(), message.getTimerMessage());
//            }
//
////            localCacheQueue.setUserTime(messages.get(0).getChatId(), messages.get(0).getTimerMessage());
//            System.out.println("Кеш наполнен: " + localCacheQueue.getAllUserTime());
//            log.info("Кеш наполнен: " + localCacheQueue.getAllUserTime());
//        }, delayMinutes, 60, TimeUnit.MINUTES);
    }
}


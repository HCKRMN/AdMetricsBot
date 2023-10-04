package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ScheduledMessageSender {
    ScheduledMessageRepository scheduledMessageRepository;
    MessageWithoutReturn messageWithoutReturn;
    MetricsMessageBuilder metricsMessageBuilder;
    CloseButtonKeyboard closeButtonKeyboard;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            List<ScheduledMessage> scheduledMessages = scheduledMessageRepository.findByTime(now);

            // Создаем пул потоков
            ExecutorService executorService = Executors.newFixedThreadPool(scheduledMessages.size());

            for (ScheduledMessage scheduledMessage : scheduledMessages) {
                String chatId = scheduledMessage.getChatId();

                executorService.submit(() ->
                    messageWithoutReturn.sendMessage(
                            chatId,
                            metricsMessageBuilder.getMessage(chatId),
                            closeButtonKeyboard.closeButtonKeyboard())
                );
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}

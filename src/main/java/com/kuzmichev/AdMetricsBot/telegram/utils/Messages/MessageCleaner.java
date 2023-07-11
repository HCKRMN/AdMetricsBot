package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageCleaner implements Runnable {
    MessageUtils messageUtils;
    HashMap<String, Integer> queueForDeletion = new HashMap<>();
    private boolean isRunning = true;

    public void putMessageToQueue(String chatId, int messageId) {
        queueForDeletion.put(chatId, messageId);
    }

    @PostConstruct
    public void startCleaningThread() {
        Thread cleaningThread = new Thread(this);
        cleaningThread.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (queueForDeletion.isEmpty()) {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    throw new RuntimeException(e);
//                }
            } else {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    throw new RuntimeException(e);
//                }

                HashMap<String, Integer> deletionMap = new HashMap<>(queueForDeletion);
                queueForDeletion.clear();

                for (String chatId : deletionMap.keySet()) {
                    int messageId = deletionMap.get(chatId);
                    messageUtils.deleteMessage(chatId, messageId);
                }
            }
        }
    }
}

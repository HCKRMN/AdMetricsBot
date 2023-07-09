package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

import java.util.HashMap;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageCleaner implements Runnable {
    MessageEditor messageEditor;
    MessageUtils messageUtils;
    HashMap<String, Integer> queueForDeletion = new HashMap<>();
    private boolean isRunning = true;

    public void putMessageToQueue(String chatId, int messageId) {
        queueForDeletion.put(chatId, messageId);
        System.out.println(queueForDeletion + " putMessageToQueue");
    }

    @PostConstruct
    public void startCleaningThread() {
        Thread cleaningThread = new Thread(this);
        cleaningThread.start();
    }

//    public void stop() {
//        isRunning = false;
//    }

    @Override
    public void run() {
        while (isRunning) {
            if (queueForDeletion.isEmpty()) {
                System.out.println(queueForDeletion + " cleanisEmpty");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println(queueForDeletion + " clean");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }

                HashMap<String, Integer> deletionMap = new HashMap<>(queueForDeletion);
                queueForDeletion.clear();
                System.out.println(deletionMap + " deletionMap");

                for (String chatId : deletionMap.keySet()) {
                    int messageId = deletionMap.get(chatId);
                    System.out.println(messageId + " messageId");
                    messageUtils.deleteMessage(chatId, messageId);
                }

                System.out.println(queueForDeletion + " clean2");
            }
        }
    }
}

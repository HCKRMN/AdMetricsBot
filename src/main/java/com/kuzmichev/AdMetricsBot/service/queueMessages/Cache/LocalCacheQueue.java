package com.kuzmichev.AdMetricsBot.service.queueMessages.Cache;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
@Service
public class LocalCacheQueue implements QueueCache {

    private HashMap<String, LocalTime> queueCache = new HashMap<>();

    @Override
    public void setUserTime(String chatId, LocalTime timerMessage) {
        queueCache.put(chatId, timerMessage);
    }

    @Override
    public LocalTime getUserTime(String chatId) {
        return queueCache.get(chatId);
    }


    @Override
    public List<String> getIdsInMinutes(LocalTime timerMessage) {
        List<String> chatIds = new ArrayList<>();

        for (Map.Entry<String, LocalTime> entry : queueCache.entrySet()) {
            if (entry.getValue().equals(timerMessage)) {
                chatIds.add(entry.getKey());
            }
        }
        return chatIds;
    }

    @Override
    public List<LocalTime> getAllUserTime() {
        return new ArrayList<>(queueCache.values());
    }

}

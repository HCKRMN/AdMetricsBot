package com.kuzmichev.AdMetricsBot.service.queueMessages.Cache;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
@Service
public class LocalCacheQueue implements QueueCache {

    private Queue<LocalTime> queueCache = new PriorityQueue<>(Comparator.naturalOrder());

    @Override
    public void setUserTime(String chatId, LocalTime timerMessage) {
        queueCache.offer(timerMessage);
    }

    @Override
    public LocalTime getUserTime(String chatId) {
        return queueCache.peek();
    }

    @Override
    public List<LocalTime> getAllUserTime() {
        return new ArrayList<>(queueCache);
    }

}

package com.kuzmichev.AdMetricsBot.service.queueMessages.Cache;

import java.time.LocalTime;
import java.util.List;

public interface QueueCache {
    void setUserTime(String chatId, LocalTime timerMessage);
    LocalTime getUserTime(String chatId);
    List<LocalTime> getAllUserTime();
}

package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity(name = "scheduledMessageTable")
@Getter
@Setter
@Cacheable
public class ScheduledMessage {
    @Id
    private String chatId;
    private LocalTime timerMessage;
    private boolean enableSendingMessages;

}

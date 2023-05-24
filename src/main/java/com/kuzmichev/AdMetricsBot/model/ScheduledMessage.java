package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

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

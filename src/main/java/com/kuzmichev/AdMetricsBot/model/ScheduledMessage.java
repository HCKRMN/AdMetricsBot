package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Entity(name = "scheduledMessageTable")
@Getter
@Setter
public class ScheduledMessage {
    @Id
    private String chatId;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timerMessage;
    private boolean enableSendingMessages;
}

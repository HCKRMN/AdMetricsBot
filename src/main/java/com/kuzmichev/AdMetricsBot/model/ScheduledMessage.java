package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity(name = "scheduledMessageTable")
@Getter
@Setter
public class ScheduledMessage {
    @Id
    private Long chatId;
    private String timerMessage;
    private boolean enableSendingMessages;


}

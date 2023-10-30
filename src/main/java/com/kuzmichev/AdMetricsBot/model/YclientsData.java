package com.kuzmichev.AdMetricsBot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString(of = {"projectId", "chatId", "newEntries", "successEntries", "canceledEntries", "requestStatus", "requestErrorMessage"})
@Component
public class YclientsData {
    private String projectId;
    private String chatId;
    private int newEntries;
    private int successEntries;
    private int canceledEntries;
    private int requestStatus;
    private String requestErrorMessage;
}

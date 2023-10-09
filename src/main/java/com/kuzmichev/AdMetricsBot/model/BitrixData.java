package com.kuzmichev.AdMetricsBot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString(of = {"projectId", "newLeads", "successDeals", "failedDeals"})
@Component
public class BitrixData {
    private String projectId;
    private String chatId;
    private int newLeads;
    private int successDeals;
    private int failedDeals;
    private int requestStatus;
}

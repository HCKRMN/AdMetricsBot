package com.kuzmichev.AdMetricsBot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString(of = {"projectId", "impressions", "ctr", "clicks", "avgCpc", "conversions", "costPerConversion", "cost", "requestStatus"})
@Component
public class YandexData {
    private String projectId;
    private int impressions;
    private double ctr;
    private int clicks;
    private double avgCpc;
    private int conversions;
    private double costPerConversion;
    private double cost;
    private int requestStatus;
}

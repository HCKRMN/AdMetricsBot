package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "biDataTable")
@Getter
@Setter
public class biData {
    @Id
    private Long chatId;
    private String biToken;
    private String biUserNum;
    private String biDomen;
}
package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "yaDataTable")
@Getter
@Setter
public class YaData {
    @Id
    private String chatId;
    private String yaToken;
    private String yaLogin;



}

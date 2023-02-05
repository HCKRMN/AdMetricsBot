package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "yaDataTable")
@Getter
@Setter
public class yaData {
    @Id
    private Long chatId;
    private String yaToken;
    private String yaLogin;

}
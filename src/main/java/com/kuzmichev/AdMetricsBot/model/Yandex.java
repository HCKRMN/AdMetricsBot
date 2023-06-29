package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "yandexTable")
@Getter
@Setter
@Cacheable
public class Yandex {
    @Id
    private String chatId;
    private String yaToken;
    private String yaLogin;
    private String projectId;

}

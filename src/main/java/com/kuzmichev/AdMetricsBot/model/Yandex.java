package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "yandexTable")
@Getter
@Setter
@Cacheable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Yandex {
    @Id
    private String projectId;
    private String chatId;
    private String yandexToken;
    private String yandexLogin;
}

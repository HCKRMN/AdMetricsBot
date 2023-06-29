package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "bitrixTable")
@Getter
@Setter
@Cacheable
public class Bitrix {
    @Id
    private String chatId;
    private String bitrixToken;
    private String bitrixUserNum;
    private String bitrixDomen;
    private String projectId;

}

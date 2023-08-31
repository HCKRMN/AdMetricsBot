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
    private String code;
    private String userId;
    private String bitrixDomain;
    private String projectId;
    private String memberId;
    private String refreshToken;
    private String accessToken;

}

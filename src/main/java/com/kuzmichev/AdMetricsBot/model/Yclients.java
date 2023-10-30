package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "yclientsTable")
@Getter
@Setter
@ToString(of = {"projectId", "chatId", "salonId", "salonName"})
@Cacheable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Yclients {
    @Id
    private String projectId;
    private String chatId;
    private String salonId;
    private String salonName;
}

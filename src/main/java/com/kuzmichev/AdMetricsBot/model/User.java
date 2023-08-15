package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity(name = "usersTable")
@Getter
@Setter
@ToString(of = {"chatId", "userName"})
@Cacheable
public class User {
    @Id
    private String chatId;
    private String userName;
    private Timestamp registeredAt;
    @Column(columnDefinition = "DOUBLE PRECISION")
    private double timeZone;
    private String ip;
    private String userState;
    private int projectsCount;
    private int projectsPage;

}

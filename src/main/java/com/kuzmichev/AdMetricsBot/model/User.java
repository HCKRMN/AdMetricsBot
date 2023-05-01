package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity(name = "usersDataTable")

@Getter
@Setter
@ToString(of = {"chatId", "userName"})
public class User {
    @Id
    private Long chatId;
    private String userName;
    private String firstName;
    private Timestamp registeredAt;
    private long timeZone;

}

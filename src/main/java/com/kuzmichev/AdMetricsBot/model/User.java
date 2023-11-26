package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "usersTable")
@Getter
@Setter
@ToString(of = {"chatId", "userName"})
@Cacheable
public class User {
    @Id
    private String chatId;
    private String userName;
    private int timeDifferenceInMinutes;
    private String ip;
}

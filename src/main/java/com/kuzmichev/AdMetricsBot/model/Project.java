package com.kuzmichev.AdMetricsBot.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "projectTable")
@Getter
@Setter
@Cacheable
public class Project {
    @Id
    private String projectId;
    private String projectName;
    private String chatId;
    private int projectNumber;
}

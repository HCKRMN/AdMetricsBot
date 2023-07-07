package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {

    List<Project> findProjectsByChatId(String chatId);

}

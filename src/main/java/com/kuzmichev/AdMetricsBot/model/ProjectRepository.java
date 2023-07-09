package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {

    List<Project> findProjectsByChatId(String chatId);
    @Transactional
    void removeProjectsByChatId(String chatId);

}

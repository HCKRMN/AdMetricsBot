package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {

    List<Project> findProjectsByChatId(String chatId);
    @Transactional
    void removeProjectsByChatId(String chatId);

    @Query("select p.projectName from projectTable p where p.projectId = :projectId")
    String findProjectNameByProjectId(String projectId);

    @Transactional
    void removeProjectByProjectId(String projectId);

    @Query("SELECT COUNT(p) FROM projectTable p WHERE p.chatId = :chatId")
    int findProjectsCountByChatId(@Param("chatId") String chatId);

    @Query("SELECT COALESCE(MAX(p.projectNumber), 0) FROM projectTable p WHERE p.chatId = :chatId")
    Long findMaxProjectNumberByChatId(@Param("chatId") String chatId);

    Project findProjectByProjectId(String projectId);


}

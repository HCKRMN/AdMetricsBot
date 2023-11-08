package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@EnableJpaRepositories
public interface YclientsRepository extends CrudRepository<Yclients,String> {

    @Transactional
    void removeYclientsByChatId(String chatId);

    @Transactional
    void removeYclientsByProjectId(String projectId);

    Yclients findYclientsByProjectId(String projectId);

    boolean existsByProjectId (String projectId);
}

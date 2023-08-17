package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BitrixRepository extends CrudRepository<Bitrix,Long> {

    @Transactional
    void removeBitrixByChatId(String chatId);

    @Transactional
    void removeBitrixByProjectId(String projectId);

    boolean existsByProjectId (String projectId);
}

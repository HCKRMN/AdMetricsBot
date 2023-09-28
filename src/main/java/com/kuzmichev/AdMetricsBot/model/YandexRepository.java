package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface YandexRepository extends CrudRepository<Yandex,String> {

    @Transactional
    void removeYandexByChatId(String chatId);

    @Transactional
    void removeYandexByProjectId(String projectId);

    boolean existsByProjectId (String projectId);

    Yandex findByProjectId(String projectId);

}

package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface YandexRepository extends CrudRepository<Yandex,String> {

    @Transactional
    void removeYandexByChatId(String chatId);

}

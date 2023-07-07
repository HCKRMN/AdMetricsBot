package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TempDataRepository extends JpaRepository<TempData, String> {

    @Query("select t.tempValue from tempDataTable t where t.chatId = :chatId")
    String findTempValueByChatId(@Param("chatId") String chatId);
}

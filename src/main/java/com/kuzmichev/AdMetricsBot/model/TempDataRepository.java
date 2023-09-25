package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TempDataRepository extends JpaRepository<TempData, String> {

    @Query("select t.lastProjectId from tempDataTable t where t.chatId = :chatId")
    String findLastProjectIdByChatId(@Param("chatId") String chatId);

    @Transactional
    void removeTempDataByChatId(String chatId);

    Optional<TempData> findByChatId(String chatId);

    @Query("select t.lastMessageId from tempDataTable t where t.chatId = :chatId")
    int findLastMessageIdByChatId(@Param("chatId") String chatId);

    @Query("select t.messagesIdsToDelete from tempDataTable t where t.chatId = :chatId")
    String findMessagesIdsToDeleteByChatId(@Param("chatId") String chatId);

    TempData getByChatId(String chatId);
}

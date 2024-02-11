package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, String> {

    Optional<ScheduledMessage> findByChatId(String chatId);

    @Query("SELECT s FROM scheduledMessageTable s WHERE s.timerMessage = :time AND s.enableSendingMessages = true")
    List<ScheduledMessage> findByTimeAndEnabled(@Param("time") LocalTime time);

    @Query("SELECT s.enableSendingMessages FROM scheduledMessageTable s WHERE s.chatId = :chatId")
    boolean findEnableSendingMessagesByChatId(@Param("chatId") String chatId);

    @Transactional
    void removeScheduledMessageByChatId(String chatId);
}

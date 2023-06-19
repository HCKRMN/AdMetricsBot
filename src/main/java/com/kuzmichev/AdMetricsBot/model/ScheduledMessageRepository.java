package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, String> {

    Optional<ScheduledMessage> findByChatId(String chatId);

    @Query("SELECT s FROM scheduledMessageTable s WHERE s.timerMessage = ?1")
    List<ScheduledMessage> findByTime(LocalTime time);

    @Query("SELECT s.enableSendingMessages FROM scheduledMessageTable s WHERE s.chatId = :chatId")
    boolean findEnableSendingMessagesByChatId(@Param("chatId") String chatId);





//    @Query("SELECT s FROM scheduledMessageTable s WHERE HOUR(s.timerMessage) = :hours AND s.enableSendingMessages = true")
//    List<ScheduledMessage> findByTimerMessageHours(@Param("hours") int hours);

    @Query("SELECT s FROM scheduledMessageTable s WHERE s.enableSendingMessages = true")
    List<ScheduledMessage> findAllByTimerMessage();

    @Query("SELECT MIN(s.timerMessage) FROM scheduledMessageTable s WHERE s.enableSendingMessages = true AND s.timerMessage > CURRENT_TIME")
    LocalTime findMinTimerMessage();



}

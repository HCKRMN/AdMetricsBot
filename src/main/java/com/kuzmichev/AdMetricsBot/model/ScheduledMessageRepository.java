package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@EnableJpaRepositories
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, String> {

    @Query("SELECT s FROM scheduledMessageTable s WHERE s.timerMessage = ?1")
    List<ScheduledMessage> findByTime(LocalTime time);

    @Query("SELECT s FROM scheduledMessageTable s WHERE HOUR(s.timerMessage) = :hours")
    List<ScheduledMessage> findByTimerMessageHours(@Param("hours") int hours);

}

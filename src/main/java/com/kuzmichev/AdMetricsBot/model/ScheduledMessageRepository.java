package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@EnableJpaRepositories
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage,String> {

    @Query("SELECT s FROM scheduledMessageTable s WHERE s.timerMessage = ?1")
    List<ScheduledMessage> findByTime(String time);

    @Query("SELECT s FROM scheduledMessageTable s WHERE FUNCTION('HOUR', s.timerMessage) = FUNCTION('HOUR', ?1)")
    List<ScheduledMessage> findByTimerMessageHour(LocalTime hour);

//    List<ScheduledMessage> findByTimerMessageHourAndMinute(LocalTime hour, int minute);

}

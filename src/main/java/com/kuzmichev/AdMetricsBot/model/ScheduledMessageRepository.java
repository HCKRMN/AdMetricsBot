package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@EnableJpaRepositories
public interface ScheduledMessageRepository extends CrudRepository<ScheduledMessage,String> {

    @Query("SELECT s FROM scheduledMessageTable s WHERE s.timerMessage = ?1")
    List<ScheduledMessage> findByTime(String time);

}

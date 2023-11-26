package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByChatId(String chatId);

    @Transactional
    void removeUserByChatId(String chatId);
}

package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByChatId(String chatId);

    @Query("SELECT u.userState FROM usersTable u WHERE u.chatId = :chatId")
    String getUserStateByChatId(@Param("chatId") String chatId);

    @Transactional
    void removeUserByChatId(String chatId);

    @Query("SELECT u.projectsCount FROM usersTable u WHERE u.chatId = :chatId")
    int getProjectsCountByChatId(String chatId);

    @Query("SELECT u.projectsPage FROM usersTable u WHERE u.chatId = :chatId")
    int getProjectsPageByChatId(String chatId);
}

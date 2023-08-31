package com.kuzmichev.AdMetricsBot.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BitrixRepository extends CrudRepository<Bitrix,Long> {

    Optional<Bitrix> findByProjectId(String projectId);

    @Transactional
    void removeBitrixByChatId(String chatId);

    @Transactional
    void removeBitrixByProjectId(String projectId);

    boolean existsByProjectId (String projectId);

    @Query("SELECT b.bitrixDomain FROM bitrixTable b WHERE b.projectId = :projectId")
    String getBitrixDomainByProjectId(String projectId);

    @Query("SELECT b.accessToken FROM bitrixTable b WHERE b.projectId = :projectId")
    String getAccessTokenByProjectId(String projectId);

    Bitrix findBitrixByProjectId(String projectId);

}

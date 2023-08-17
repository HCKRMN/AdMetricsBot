package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DeleteUserData {
    BitrixRepository bitrixRepository;
    ScheduledMessageRepository scheduledMessageRepository;
    UserRepository userRepository;
    YandexRepository yandexRepository;
    ProjectRepository projectRepository;
    TempDataRepository tempDataRepository;

    public void deleteUserData(String chatId) {
        userRepository.removeUserByChatId(chatId);
        bitrixRepository.removeBitrixByChatId(chatId);
        scheduledMessageRepository.removeScheduledMessageByChatId(chatId);
        yandexRepository.removeYandexByChatId(chatId);
        projectRepository.removeProjectsByChatId(chatId);
        tempDataRepository.removeTempDataByChatId(chatId);
        log.info("Пользователь с id: {} удален", chatId);
    }
}

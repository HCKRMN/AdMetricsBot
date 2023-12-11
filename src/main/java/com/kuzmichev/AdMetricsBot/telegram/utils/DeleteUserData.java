package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
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
    YclientsRepository yclientsRepository;
    UserStateKeeper userStateKeeper;

    public void deleteUserData(String chatId) {
        userRepository.removeUserByChatId(chatId);
        scheduledMessageRepository.removeScheduledMessageByChatId(chatId);

        projectRepository.removeProjectsByChatId(chatId);

        bitrixRepository.removeBitrixByChatId(chatId);
        yandexRepository.removeYandexByChatId(chatId);
        yclientsRepository.removeYclientsByChatId(chatId);

        userStateKeeper.deleteState(chatId);
        log.info("Пользователь с id: {} удален", chatId);
    }
}

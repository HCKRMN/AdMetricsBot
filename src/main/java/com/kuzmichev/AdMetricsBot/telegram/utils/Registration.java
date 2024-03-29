package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Registration{
    ScheduledMessageRepository scheduledMessageRepository;
    UserRepository userRepository;

    public void registerUser(String chatId, String userName) {
        if (scheduledMessageRepository.findById(chatId).isEmpty()) {
            ScheduledMessage scheduledMessage = new ScheduledMessage();
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setEnableSendingMessages(false);
            scheduledMessageRepository.save(scheduledMessage);

            User user = new User();
            user.setChatId(chatId);
            user.setUserName(userName);
            userRepository.save(user);

            log.info("Пользователь {} сохранен" , chatId);

        } else {
            log.info("Пользователь {} уже зарегистрирован" , chatId);
        }
    }
}
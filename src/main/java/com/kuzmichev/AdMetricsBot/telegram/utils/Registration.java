package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.UserStatesEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Registration{
    ScheduledMessageRepository scheduledMessageRepository;
    UserRepository userRepository;

    public void registerUser(String chatId, String userName) {
        if(userRepository.findById(chatId).isEmpty()) {
            User user = new User();
            ScheduledMessage scheduledMessage = new ScheduledMessage();
            user.setChatId(chatId);
            user.setUserName(userName);
            user.setUserState(UserStatesEnum.REGISTRATION_STATE.getStateName());
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setEnableSendingMessages(false);
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            scheduledMessageRepository.save(scheduledMessage);
            log.info("user saved: " + user);
        }
    }
}
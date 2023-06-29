package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserStateEditor {
    UserRepository userRepository;
    public void editUserState(String chatId, String userState) {
        Optional<User> userOptional = userRepository.findByChatId(chatId);
        User user = userOptional.get();
        user.setUserState(userState);
        userRepository.save(user);
    }
}

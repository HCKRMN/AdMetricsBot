package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeZoneDefinition {
    UserRepository userRepository;

    public void manualTimeZone(String chatId, String messageText) {
        LocalTime timerMessage = LocalTime.parse(messageText.replace(" ", ":"));
        System.out.println("Время из сообщения: " + timerMessage);
        LocalTime timeZone = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        System.out.println("Время сейчас: " + timeZone);

        int timeDifferenceInMinutes = Integer.parseInt(String.valueOf(Duration.between(timeZone, timerMessage).toMinutes()));
        System.out.println("Разница: " + timeDifferenceInMinutes);

        Optional<User> userOptional = userRepository.findById(chatId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setTimeDifferenceInMinutes(timeDifferenceInMinutes);
            userRepository.save(user);
        }
    }
}

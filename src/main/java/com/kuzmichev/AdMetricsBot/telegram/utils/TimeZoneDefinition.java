package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
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
        Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));
        LocalTime timeZone = LocalTime.parse(LocalTime.now(clock).format(DateTimeFormatter.ofPattern("HH:mm")));

        int timeDifferenceInMinutes = Integer.parseInt(String.valueOf(Duration.between(timeZone, timerMessage).toMinutes()));

        Optional<User> userOptional = userRepository.findById(chatId);
        User user = userOptional.orElseGet(User::new);
        user.setChatId(chatId);
        user.setTimeDifferenceInMinutes(timeDifferenceInMinutes);
        userRepository.save(user);
    }
}

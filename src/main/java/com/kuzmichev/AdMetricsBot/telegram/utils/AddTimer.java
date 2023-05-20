package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddTimer {
    UserRepository userRepository;
    YaRepository yaRepository;
    ScheduledMessageRepository scheduledMessageRepository;

    public SendMessage setTimerAndStart(String chatId, String messageText) {

        double hoursDecimal = userRepository.findById(chatId).get().getTimeZone(); // получаем временную зону
        int hours = (int) Math.floor(hoursDecimal); // округляем до меньшего целого
        int minutes = (int) Math.round((hoursDecimal - hours) * 60); // получаем дробную часть в минутах, округляем
        LocalTime timeZone = LocalTime.of(hours, minutes); // создаем новый объект LocalTime
        LocalTime timerMessage = LocalTime.
                parse(messageText.replace(" ", ":")).
                minusHours(timeZone.getHour()).
                minusMinutes(timeZone.getMinute());

        System.out.println(timerMessage);

        YaData yaData = yaRepository.findById(chatId).orElse(null);
        if (yaData != null && yaData.getYaToken() != null) {
            ScheduledMessage scheduledMessage = new ScheduledMessage();
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setTimerMessage(timerMessage);
            scheduledMessage.setEnableSendingMessages(true);
            scheduledMessageRepository.save(scheduledMessage);
            log.info("User set timer at " + timerMessage);
            return new SendMessage(chatId, BotMessageEnum.TIMER_ADDED_MESSAGE.getMessage() + messageText.replace(" ", ":"));
        } else
            return new SendMessage(chatId, BotMessageEnum.ERRORE_YANDEX_MESSAGE.getMessage());

    }

}

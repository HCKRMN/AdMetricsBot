package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddTimer {
    UserRepository userRepository;
    ScheduledMessageRepository scheduledMessageRepository;
    UserStateEditor userStateEditor;

    /**
     * Устанавливает таймер и запускает его.
     *
     * @param chatId       идентификатор чата
     * @param messageText  текст сообщения, которое содержит время отправки в формате «23 59»
     * @return сообщение о добавлении таймера
     */

    public SendMessage setTimerAndStart(String chatId, String messageText) {

        double hoursDecimal = userRepository.findById(chatId).get().getTimeZone(); // получаем временную зону
        int hours = (int) Math.floor(hoursDecimal); // округляем до меньшего целого
        int minutes = (int) Math.round((hoursDecimal - hours) * 60); // получаем дробную часть в минутах, округляем
        LocalTime timeZone = LocalTime.of(hours, minutes); // создаем новый объект LocalTime
        LocalTime timerMessage = LocalTime.parse(messageText.replace(" ", ":"))
                .minusHours(timeZone.getHour())
                .minusMinutes(timeZone.getMinute());

        Optional<ScheduledMessage> scheduledMessageOptional = scheduledMessageRepository.findByChatId(chatId);
        ScheduledMessage scheduledMessage = scheduledMessageOptional.get();
        scheduledMessage.setTimerMessage(timerMessage);
        scheduledMessageRepository.save(scheduledMessage);

        // Изменяем статус юзера на рабочий
        userStateEditor.editUserState(chatId, UserStateEnum.WORKING_STATE);

        log.info("User set timer at " + timerMessage);
        return new SendMessage(chatId, BotMessageEnum.TIMER_ADDED_MESSAGE.getMessage() + messageText.replace(" ", ":"));

    }

}

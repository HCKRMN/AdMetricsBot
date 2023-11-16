package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
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
    UserStateKeeper userStateKeeper;
    TimeZoneKeyboard timeZoneKeyboard;
    DoneButtonKeyboard doneButtonKeyboard;

    /**
     * Устанавливает таймер и запускает его.
     *
     * @param chatId       идентификатор чата
     * @param messageText  текст сообщения, которое содержит время отправки в формате «23 59»
     * @return сообщение о добавлении таймера
     */

    public SendMessage setTimerAndStart(String chatId, String messageText, String state) {
        // Если юзер найден
        Optional<User> user = userRepository.findById(chatId);
        if (user.isPresent()){
            int timeDifferenceInMinutes = user.get().getTimeDifferenceInMinutes(); // получаем разницу часовых поясов в минутах
            LocalTime timerMessage = LocalTime.parse(messageText.replace(" ", ":"))
                .minusMinutes(timeDifferenceInMinutes); // добавляем разницу часовых поясов к таймеру

            ScheduledMessage scheduledMessage = new ScheduledMessage();
            scheduledMessage.setTimerMessage(timerMessage);
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setEnableSendingMessages(true);
            scheduledMessageRepository.save(scheduledMessage);

            // Изменяем статус юзера на рабочий
            userStateKeeper.setState(chatId, StateEnum.WORKING_STATE.getStateName());

            log.info("Пользователь {} установил таймер на " + timerMessage, chatId);

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.TIMER_ADDED_MESSAGE.getMessage() + messageText.replace(" ", ":"))
                    .replyMarkup(doneButtonKeyboard.getKeyboard(state, chatId))
                    .build();

        }

        return SendMessage.builder()
                .chatId(chatId)
                .text(MessageEnum.TIMER_ADD_ERROR_MESSAGE.getMessage())
                .replyMarkup(timeZoneKeyboard.getKeyboard(state, chatId))
                .build();
    }
}

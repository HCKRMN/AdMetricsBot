package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
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
    MessageWithReturn messageWithReturn;
    TimeZoneKeyboard timeZoneKeyboard;
    DoneButtonKeyboard doneButtonKeyboard;

    /**
     * Устанавливает таймер и запускает его.
     *
     * @param chatId       идентификатор чата
     * @param messageText  текст сообщения, которое содержит время отправки в формате «23 59»
     * @return сообщение о добавлении таймера
     */

    public SendMessage setTimerAndStart(String chatId, String messageText, String userState) {
        // Если юзер найден
        Optional<User> user = userRepository.findById(chatId);
        if (user.isPresent()){
            int timeDifferenceInMinutes = user.get().getTimeDifferenceInMinutes(); // получаем разницу часовых поясов в минутах
            LocalTime timerMessage = LocalTime.parse(messageText.replace(" ", ":"))
                .plusMinutes(timeDifferenceInMinutes); // добавляем разницу часовых поясов к таймеру

            ScheduledMessage scheduledMessage = new ScheduledMessage();
            scheduledMessage.setTimerMessage(timerMessage);
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setEnableSendingMessages(true);
            scheduledMessageRepository.save(scheduledMessage);

            // Изменяем статус юзера на рабочий
            userStateEditor.editState(chatId, StateEnum.WORKING_STATE.getStateName());

            log.info("Пользователь {} установил таймер на " + timerMessage, chatId);
            return messageWithReturn.sendMessage(
                    chatId,
                    MessageEnum.TIMER_ADDED_MESSAGE.getMessage() + messageText.replace(" ", ":"),
                    doneButtonKeyboard.doneButtonMenu(),
                    StateEnum.WORKING_STATE.getStateName());

        }
        return messageWithReturn.sendMessage(
                chatId,
                MessageEnum.TIMER_ADD_ERROR_MESSAGE.getMessage(),
                timeZoneKeyboard.timeZoneKeyboard(chatId, userState),
                null
        );
    }

}

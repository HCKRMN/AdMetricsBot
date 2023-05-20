package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.service.queueMessages.Cache.LocalCacheQueue;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StartCommandReceived {
    InlineKeyboardMaker inlineKeyboardMaker;
    ScheduledMessageRepository scheduledMessageRepository;
    LocalCacheQueue localCacheQueue;



    public SendMessage sendGreetingMessage(String chatId, String firstName) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage());

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(inlineKeyboardMaker.getButton(
                ButtonNameEnum.REGISTRATION_BUTTON.getButtonName(),
                "START_REGISTRATION",
                null));
        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);

        log.info("Пользователь {} с id: {} стартует.", firstName, chatId);

        int x = 0;
            LocalTime nextHour = LocalTime.now().plusHours(x);
        System.out.println(nextHour);
            int hour = nextHour.getHour();
        System.out.println(hour);


            List<ScheduledMessage> messages = scheduledMessageRepository.findByTimerMessageHours(hour);
            System.out.println("Не сортирован " + messages);
            // Сортируем список messages по возрастанию timerMessage

        System.out.println("Вроде как сортирован " + messages);
            // Наполняем кеш LocalCacheQueue
            for (ScheduledMessage message : messages) {
                localCacheQueue.setUserTime(message.getChatId(), message.getTimerMessage());
                System.out.println(localCacheQueue.getAllUserTime());
            }
            log.info("Кеш наполнен: " + localCacheQueue.getAllUserTime());
        System.out.println(localCacheQueue.getUserTime(chatId));





        return sendMessage;
    }
}

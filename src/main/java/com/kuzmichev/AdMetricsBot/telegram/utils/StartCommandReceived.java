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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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







        LocalTime now = LocalTime.now();
        LocalTime nextHour = now.plusHours(1);
        List<ScheduledMessage> messages = scheduledMessageRepository.findByTimerMessageHour(nextHour);
        System.out.println(3);
        System.out.println(messages);
        // Отсортировать список messages по возрастанию timerMessage
        messages.sort(Comparator.comparing(ScheduledMessage::getTimerMessage));
        System.out.println(messages);
        System.out.println(4);
        // Заполнить LocalCacheQueue
        for (ScheduledMessage message : messages) {
            localCacheQueue.setUserTime(message.getChatId(), message.getTimerMessage());
        }

//            localCacheQueue.setUserTime(messages.get(0).getChatId(), messages.get(0).getTimerMessage());
        System.out.println("Кеш наполнен: " + localCacheQueue.getAllUserTime());
        log.info("Кеш наполнен: " + localCacheQueue.getAllUserTime());











        return sendMessage;
    }
}

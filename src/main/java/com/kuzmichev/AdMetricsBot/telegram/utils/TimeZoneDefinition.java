package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeZoneDefinition {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final InlineKeyboards inlineKeyboards;
    final UserRepository userRepository;
    final ScheduledMessageRepository scheduledMessageRepository;
    final MessageWithReturn messageWithReturn;
    @Value("${telegram.webhook-path}")
    String link;

    public SendMessage requestTimeZoneSettingLink(String chatId) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage());

        String state = userRepository.getUserStateByChatId(chatId);

        // Создаем пустой список для кнопок
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        // Добавляем кнопку "Ссылка" всегда
        buttonsRow.add(inlineKeyboardMaker.addButton(
                ButtonNameEnum.LINK_BUTTON.getButtonName(),
                null,
                ipToTimeZoneLink
        ));

        // Добавляем кнопку "Continue" только если условие выполняется
        if (state.equals(UserStateEnum.SETTINGS_EDIT_STATE.getStateName())) {
            buttonsRow.add(inlineKeyboardMaker.addButton(
                    ButtonNameEnum.CONTINUE_BUTTON.getButtonName(),
                    CallBackEnum.PROJECT_CREATE_CALLBACK.getCallBackName(),
                    null
            ));
        }

        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(buttonsRow)
                )
        );

        log.info("Пользователь с id: {} устанавливает временную зону.", chatId);
        return sendMessage;
    }

    public SendMessage manualTimeZone(String chatId, String messageText) {
        LocalTime timerMessage = LocalTime.parse(messageText.replace(" ", ":"));
        System.out.println(timerMessage + "timerMessage");
        LocalTime timeZone = LocalTime.now();
        System.out.println(timeZone + "timeZone");

        Duration duration = Duration.between(timerMessage, timeZone);
        LocalTime resultTime = timeZone.plusSeconds(duration.getSeconds());
        System.out.println(resultTime + "resultTime");

        ScheduledMessage scheduledMessage = new ScheduledMessage();
        scheduledMessage.setChatId(chatId);
        scheduledMessage.setTimerMessage(resultTime);
        scheduledMessageRepository.save(scheduledMessage);

        return messageWithReturn.sendMessage(
                chatId,
                BotMessageEnum.TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage(),
                inlineKeyboards.done(),
                UserStateEnum.WORKING_STATE
        );
    }
}

package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalButtonEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessage;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.InlineKeyboardMaker;
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
    final DoneButtonKeyboard doneButtonKeyboard;
    final UserRepository userRepository;
    final ScheduledMessageRepository scheduledMessageRepository;
    final MessageWithReturn messageWithReturn;
    @Value("${telegram.webhook-path}")
    String link;

    public SendMessage requestTimeZoneSettingLink(String chatId) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        SendMessage sendMessage = new SendMessage(chatId, SettingsMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage());

        String state = userRepository.getUserStateByChatId(chatId);

        // Создаем пустой список для кнопок
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        // Добавляем кнопку "Ссылка" всегда
        buttonsRow.add(inlineKeyboardMaker.addButton(
                UniversalButtonEnum.LINK_BUTTON.getButtonName(),
                null,
                ipToTimeZoneLink
        ));

        // Добавляем кнопку "Continue" только если условие выполняется
        if (state.equals(SettingsStateEnum.SETTINGS_EDIT_STATE.getStateName())) {
            buttonsRow.add(inlineKeyboardMaker.addButton(
                    SettingsButtonEnum.CONTINUE_BUTTON.getButtonName(),
                    SettingsCallBackEnum.PROJECT_CREATE_CALLBACK.getCallBackName(),
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
                SettingsMessageEnum.TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage(),
                doneButtonKeyboard.doneButtonMenu(),
                SettingsStateEnum.WORKING_STATE.getStateName()
        );
    }
}

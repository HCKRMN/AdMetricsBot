package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.service.MetricsMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.SettingsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.StartRegistrationKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommandHandler {
    SettingsKeyboard settingsKeyboard;
    MessageWithoutReturn messageWithoutReturn;
    MessageWithReturn messageWithReturn;
    StartRegistrationKeyboard startRegistrationKeyboard;
    Registration registration;
    TimeZoneKeyboard timeZoneKeyboard;
    MetricsMessageBuilder metricsMessageBuilder;
    CloseButtonKeyboard closeButtonKeyboard;

    public BotApiMethod<?> handleUserCommand(Message message, String userState) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();
        String userName = message.getFrom().getUserName();
        CommandEnum commandEnum = CommandEnum.fromCommand(messageText);


        if (commandEnum != null) {
            switch (commandEnum) {
                case START -> {
                    return messageWithReturn.sendMessage(
                            chatId,
                            MessageEnum.START_MESSAGE.getMessage(),
                            startRegistrationKeyboard.startRegistrationKeyboard(),
                            StateEnum.REGISTRATION_STATE.getStateName()
                    );
                }
                case SETTINGS -> {
                    return messageWithReturn.sendMessage(
                            chatId,
                            MessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                            settingsKeyboard.settingsMenu(chatId),
                            StateEnum.SETTINGS_EDIT_STATE.getStateName());
                }
                case HELP -> {
                    return new SendMessage(chatId, MessageEnum.HELP_MESSAGE.getMessage());
                }
                case REGISTER -> {
                    registration.registerUser(chatId, userName);
                    return messageWithReturn.sendMessage(
                            chatId,
                            MessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage(),
                            timeZoneKeyboard.timeZoneKeyboard(chatId, userState),
                            null);
                }
                case TEST -> {
                    return messageWithReturn.sendMessage(
                            chatId,
                            metricsMessageBuilder.getMessage(chatId),
                            closeButtonKeyboard.closeButtonKeyboard(),
                            null);
                }
                default -> {
                    messageWithoutReturn.sendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
                    return new SendMessage(chatId, MessageEnum.HELP_MESSAGE.getMessage());
                }
            }
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

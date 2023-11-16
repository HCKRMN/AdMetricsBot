package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers;

import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.service.MetricsMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.SettingsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.StartRegistrationKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommandHandler {
    SettingsKeyboard settingsKeyboard;
    StartRegistrationKeyboard startRegistrationKeyboard;
    Registration registration;
    TimeZoneKeyboard timeZoneKeyboard;
    MetricsMessageBuilder metricsMessageBuilder;
    UserStateKeeper userStateKeeper;
    CloseButtonKeyboard closeButtonKeyboard;
    ProjectsDataTempKeeper projectsDataTempKeeper;

    public BotApiMethod<?> handleUserCommand(Message message, String userState) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();
        String userName = message.getFrom().getUserName();
        CommandEnum commandEnum = CommandEnum.fromCommand(messageText);

        if (commandEnum != null) {
            switch (commandEnum) {
                case START -> {
                    userStateKeeper.setState(chatId, StateEnum.REGISTRATION_STATE.getStateName());
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.START_MESSAGE.getMessage())
                            .replyMarkup(startRegistrationKeyboard.getKeyboard(userState, chatId))
                            .build();
                }

                case SETTINGS -> {
                    userStateKeeper.setState(chatId, StateEnum.SETTINGS_STATE.getStateName());
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.SETTINGS_MENU_MESSAGE.getMessage())
                            .replyMarkup(settingsKeyboard.getKeyboard(userState, chatId))
                            .build();
                }

                case HELP -> {
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.HELP_MESSAGE.getMessage())
                            .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                            .build();
                }

                case REGISTER -> {
                    registration.registerUser(chatId, userName);
                    userStateKeeper.setState(chatId, StateEnum.REGISTRATION_EDIT_TIMEZONE_STATE.getStateName());
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage())
                            .replyMarkup(timeZoneKeyboard.getKeyboard(userState, chatId))
                            .build();
                }

                case TEST -> {
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(metricsMessageBuilder.getAllProjectsMessage(chatId))
                            .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                            .build();
                }

                default -> {
                    return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
                }
            }
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

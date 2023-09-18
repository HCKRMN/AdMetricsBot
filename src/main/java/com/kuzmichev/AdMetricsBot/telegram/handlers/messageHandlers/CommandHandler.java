package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMainRequest;
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

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommandHandler {
    SettingsKeyboard settingsKeyboard;
    MessageWithoutReturn messageWithoutReturn;
    MessageWithReturn messageWithReturn;
    TempDataRepository tempDataRepository;
    BitrixMainRequest bitrixMainRequest;
    StartRegistrationKeyboard startRegistrationKeyboard;
    Registration registration;
    TimeZoneKeyboard timeZoneKeyboard;
    UserRepository userRepository;
    public BotApiMethod<?> handleUserCommand(Message message) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();
        String userName = message.getFrom().getUserName();
        String userState = userRepository.getUserStateByChatId(chatId);
        CommandEnum commandEnum = CommandEnum.fromCommand(messageText);

        if (commandEnum != null) {
            switch (commandEnum) {
                case START -> {
                    return messageWithReturn.sendMessage(
                            chatId,
                            RegistrationMessageEnum.START_MESSAGE.getMessage(),
                            startRegistrationKeyboard.startRegistrationKeyboard(),
                            RegistrationStateEnum.REGISTRATION_STATE.getStateName());
                }
                case SETTINGS -> {
                    return messageWithReturn.sendMessage(
                            chatId,
                            SettingsMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                            settingsKeyboard.settingsMenu(chatId),
                            SettingsStateEnum.SETTINGS_EDIT_STATE.getStateName());
                }
                case HELP -> {
                    return new SendMessage(chatId, SettingsMessageEnum.HELP_MESSAGE.getMessage());
                }
                case REGISTER -> {
                    registration.registerUser(chatId, userName);
                    return messageWithReturn.sendMessage(
                            chatId,
                            SettingsMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage(),
                            timeZoneKeyboard.timeZoneKeyboard(chatId, userState),
                            null);
                }
                case TEST -> {
//                    Тест битрикса
                    String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
                    return new SendMessage(chatId, "ЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫ: " + bitrixMainRequest.mainBitrixRequest(projectId));
                }
                default -> {
                    messageWithoutReturn.sendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
                    return new SendMessage(chatId, SettingsMessageEnum.HELP_MESSAGE.getMessage());
                }
            }
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

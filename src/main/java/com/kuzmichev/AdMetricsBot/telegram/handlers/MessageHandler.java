package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.config.TelegramConfig;
import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.InputsEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.*;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.vdurmont.emoji.EmojiParser;
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
public class MessageHandler {
    AddTimer addTimer;
    Registration registration;
    TelegramConfig config;
    UserRepository userRepository;
    StartCommandReceived startCommandReceived;
    TimeZoneDefinition timeZoneDefinition;
    SettingsMenu settingsMenu;
    ProjectManager projectManager;
    Validator validator;
    UserStateEditor userStateEditor;
    MessageManagementService messageManagementService;
    TempDataRepository tempDataRepository;
    InlineKeyboards inlineKeyboards;
    MessageWithReturn messageWithReturn;
    MessageWithoutReturn messageWithoutReturn;
    InputsManager inputsManager;
    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String userName = message.getFrom().getUserName();
        String messageText = message.getText();
        String userState = userRepository.getUserStateByChatId(chatId);
        CommandEnum commandEnum = CommandEnum.fromCommand(messageText);

        // Ловим команду отправки сообщения от админа
        if (messageText.contains("/send") && chatId.equals(config.getOwnerId())){
            var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
            var users = userRepository.findAll();
            for (User user : users) {
                return new SendMessage(user.getChatId(), textToSend);
            }
        }
        // Обработка пользовательских комманд
        else if (commandEnum != null) {
                switch (commandEnum) {
                    case START -> {
                        return startCommandReceived.sendGreetingMessage(chatId, message.getChat().getFirstName());
                    }
                    case SETTINGS -> {
                        return settingsMenu.SettingsMenuMaker(chatId);
                    }
                    case HELP -> {
                        // Обработка команды "/help"
                    }
                    case REGISTER -> {
                        registration.registerUser(chatId, userName);
                        return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
                    }
                    case TEST -> {
//                        messageManagementService.deleteMessage(chatId);
////                        return addYandex.testYandex(yandexRepository, chatId);

                        String projectId = "07fcd821-668b-4bb0-be86-5a1afaeec507";
                        String inputName = InputsEnum.Yandex.getInputName();

                        inputsManager.deleteInputs(projectId, inputName);
                    }
                    default -> {
                    }
                }
            }

        // Обработка команд при разных состояниях пользователя
        else if (userState != null) {
            UserStateEnum userStateEnum = UserStateEnum.valueOf(userState);
            int messageId = tempDataRepository.findLastMessageIdByChatId(chatId);
            messageManagementService.putMessageToQueue(chatId, messageId);
            messageManagementService.deleteMessage(chatId);
            switch (userStateEnum) {
                // Ловим и валидируем время таймера
                case SETTINGS_EDIT_TIMER_STATE -> {
                    if (validator.validateTime(messageText)) {
                        return addTimer.setTimerAndStart(chatId, messageText);
                    } else {
                        messageManagementService.putMessageToQueue(chatId, messageId);
                        SendMessage sendMessage = messageWithReturn.sendMessage(
                                chatId,
                                BotMessageEnum.INVALID_TIME_MESSAGE.getMessage(),
                                inlineKeyboards.backAndExitMenu(chatId),
                                null);
                        messageWithoutReturn.sendMessage(sendMessage);
                        return null;
                    }
                }
                // Ловим и валидируем имя проекта
                case SETTINGS_PROJECT_CREATE_ASK_NAME_STATE -> {
                    if (validator.validateProjectName(messageText)) {
                        userStateEditor.editUserState(chatId, UserStateEnum.SETTINGS_PROJECT_ADD_TOKENS_STATE);
                        return projectManager.projectCreate(chatId, messageText);
                    } else {
                        SendMessage sendMessage = messageWithReturn.sendMessage(
                                chatId,
                                BotMessageEnum.PROJECT_NAME_INVALID_MESSAGE.getMessage(),
                                inlineKeyboards.backAndExitMenu(chatId),
                                null);
                        messageWithoutReturn.sendMessage(sendMessage);
                        return null;
                    }
                }
                // Речной ввод времени пользователя
                case EDIT_TIMEZONE_MANUAL_STATE -> {
                    if (validator.validateTime(messageText)) {
                        return timeZoneDefinition.manualTimeZone(chatId, messageText);
                    } else {
                        SendMessage sendMessage = messageWithReturn.sendMessage(
                                chatId,
                                BotMessageEnum.INVALID_TIME_MESSAGE.getMessage(),
                                inlineKeyboards.backAndExitMenu(chatId),
                                null);
                        messageWithoutReturn.sendMessage(sendMessage);
                        return null;
                    }
                }
                default -> {
                }
            }
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
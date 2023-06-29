package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.config.TelegramConfig;
import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.telegram.utils.*;
import com.vdurmont.emoji.EmojiParser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    AddTimer addTimer;
    Registration registration;
    TelegramConfig config;
    UserRepository userRepository;
    YandexRepository yandexRepository;
    StartCommandReceived startCommandReceived;
    TimeZoneDefinition timeZoneDefinition;
    AddYandex addYandex;
    SettingsMenu settingsMenu;
    BotMessageUtils botMessageUtils;
    ProjectManager projectManager;
    Validator validator;


    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String userName = message.getFrom().getUserName();
        String messageText = message.getText();
        String userState = userRepository.getUserStateByChatId(chatId);
        UserStateEnum userStateEnum = UserStateEnum.valueOf(userState);

        // Ловим команду отправки сообщения от админа
        if (messageText.contains("/send") && chatId.equals(config.getOwnerId())){
            var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
            var users = userRepository.findAll();
            for (User user : users) {
                return new SendMessage(user.getChatId(), textToSend);
            }
        }
        // Обработка команд при разных состояниях пользователя
        else if (userState != null) {
            switch (userStateEnum) {
                // Ловим и валидируем время таймера
                case SETTINGS_EDIT_TIMER_STATE -> {
                    if (validator.validateTime(messageText)) {
                        return addTimer.setTimerAndStart(chatId, messageText);
                    } else {
                        return new SendMessage(chatId, BotMessageEnum.INVALID_TIME_MESSAGE.getMessage());
                    }
                }
                // Ловим и валидируем имя проекта
                case PROJECT_CREATE_NAME_STATE -> {
                    if (validator.validateProjectName(messageText)) {
                        projectManager.projectCreate(chatId, messageText);
                        return new SendMessage(chatId, BotMessageEnum.PROJECT_CREATE_DONE_MESSAGE.getMessage());
                    } else {
                        return new SendMessage(chatId, BotMessageEnum.PROJECT_NAME_INVALID_MESSAGE.getMessage());
                    }
                }
                case SETTINGS_EDIT_STATE -> {
                    // Действия для SETTINGS_EDIT_STATE
                }
            }
        }

        else {
            switch (Objects.requireNonNull(CommandEnum.fromCommand(messageText))) {
                case START -> {
                    return startCommandReceived.sendGreetingMessage(chatId, message.getChat().getFirstName());
                }
                case SETTINGS -> {
                    return settingsMenu.menuMaker(chatId);
                }
                case HELP -> {
                    // Обработка команды "/help"
                }
                case REGISTER -> {
                    registration.registerUser(chatId, userName);
                    return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
                }
                case TEST -> {
                    return addYandex.testYaData(yandexRepository, chatId);
                }
                default -> {}
            }

        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
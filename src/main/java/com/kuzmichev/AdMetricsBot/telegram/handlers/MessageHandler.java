package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.config.TelegramConfig;
import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import com.kuzmichev.AdMetricsBot.service.YandexDirectRequest;
import com.kuzmichev.AdMetricsBot.telegram.TelegramApiClient;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.ReplyKeyboardMaker;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class MessageHandler {
    AddTimer addTimer;
    Registration registration;
    TelegramConfig config;
    UserRepository userRepository;
    YaRepository yaRepository;
    StartCommandReceived startCommandReceived;
    TimeZoneDefinition timeZoneDefinition;
    AddYandex addYandex;
    SettingsMenu settingsMenu;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String userName = message.getFrom().getUserName();
        String messageText = message.getText();

        if(messageText.contains("/send") && config.getOwnerId() == chatId) {
            var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
            var users = userRepository.findAll();
            for (User user : users) {
                return new SendMessage(user.getChatId(), textToSend);
            }
        }

        else if (messageText.matches("(\\d{1,2} \\d{1,2})")) {
            if (messageText.matches("((?:[01]\\d|2[0-3]) (?:[0-5]\\d))|((?:[0-9]|1\\d|2[0-3]) (?:[0-5]\\d))\n")) {
                return addTimer.setTimerAndStart(chatId, messageText);
            } else {
                return new SendMessage(chatId, BotMessageEnum.INVALID_TIME_MESSAGE.getMessage());
            }
        }

        else {
            switch (messageText) {
                case "/start" -> {
                    return startCommandReceived.sendGreetingMessage(chatId, message.getChat().getFirstName());
                }
                case "/settings" -> {
                    return settingsMenu.menuMaker(chatId);
                }
                case "/help" -> {

                }
                case "/register" -> {
                    registration.registerUser(chatId, userName);
                    return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
                }
                case "/test" -> {
                    return  addYandex.testYaData(yaRepository, chatId);
                }
                default -> {}
            }
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
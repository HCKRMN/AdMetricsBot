package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.telegram.TelegramApiClient;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.ReplyKeyboardMaker;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
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
    Registration registration;
    InlineKeyboardMaker inlineKeyboardMaker;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String userName = message.getFrom().getUserName();
        String messageText = message.getText();
        switch (messageText) {
            case "/start":
                return startCommandReceived(chatId, message.getChat().getFirstName());
            case "/register":
                registration.registerUser(chatId, userName);
                break;
            case "/test":
                System.out.println("Команда тестовая");
                break;
            default:
                System.out.println("Неизвестная команда");
                break;
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }


    private SendMessage startCommandReceived(String chatId, String firstName) {
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
        return sendMessage;
    }
}
package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers;

import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SendAllUsersHandler {
    UserRepository userRepository;
    MessageWithoutReturn messageWithoutReturn;
    public BotApiMethod<?> handleAdminCommand(String chatId, String messageText) {
        String textToSend = messageText.substring(messageText.indexOf(" "));
        var users = userRepository.findAll();
        System.out.println(users);
        for (User user : users) {
            messageWithoutReturn.sendMessage(user.getChatId(), textToSend);
        }
        return new SendMessage(chatId, "Сообщения отправлены");
    }
}

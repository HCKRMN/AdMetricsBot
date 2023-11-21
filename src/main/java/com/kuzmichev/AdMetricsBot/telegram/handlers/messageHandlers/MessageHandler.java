package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers;

import com.kuzmichev.AdMetricsBot.config.TelegramConfig;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler.StateHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler.StateHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    TelegramConfig config;
    UserRepository userRepository;
    SendAllUsersHandler sendAllUsersHandler;
    CommandHandler commandHandler;
    StateHandlersList stateHandlersList;
    MessageManagementService messageManagementService;
    UserStateKeeper userStateKeeper;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();
        String userState = userStateKeeper.getState(chatId);
        int messageIdFromUser = message.getMessageId();

        System.out.println("Сообщение которое пришло:");
        System.out.println(messageIdFromUser);

        // Ловим контакт пользователя
        if(message.hasContact()){
            long phoneNumber = Long.parseLong(message.getContact().getPhoneNumber());
            Optional<User> userOptional = userRepository.findByChatId(chatId);
            User user = userOptional.orElseGet(User::new);
            user.setChatId(chatId);
            user.setPhoneNumber(phoneNumber);
            userRepository.save(user);

            // Удаляем старые сообщения
            int lastSentMessageId = messageIdFromUser-1;
            System.out.println("Добавляем сообщение в очередь до отправки контакта пользователем: " + lastSentMessageId);
            messageManagementService.putMessageToQueue(chatId, lastSentMessageId);

            System.out.println("Удаляем сообщения при обнаружении контакта");
            messageManagementService.deleteMessage(chatId);

            int nextMessageId = messageIdFromUser+1;
            System.out.println("Добавляем сообщение со ссылкой yclients в очередь: " + nextMessageId);
            messageManagementService.putMessageToQueue(chatId, nextMessageId);

            // Проверяем, есть ли текущее состояние у пользователя
            if (userState != null) {
                for (StateHandler stateHandler : stateHandlersList.getStateHandlers()) {
                    if (stateHandler.canHandle(userState)) {
                        return stateHandler.handleState(chatId, messageText, userState);
                    }
                }
            }
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.CONTACT_SAVE_MESSAGE.getMessage())
                    .build();
        }

        System.out.println("Удаляем сообщения из метода answerMessage");
        messageManagementService.deleteMessage(chatId);
        int nextMessageId = messageIdFromUser+1;
        System.out.println("Добавляем сообщение в очередь из метода answerMessage: " + nextMessageId);
        messageManagementService.putMessageToQueue(chatId, nextMessageId);

        // Ловим команду отправки сообщения от админа
        if (messageText.contains("/send") && chatId.equals(config.getOwnerId())){
            return sendAllUsersHandler.handleAdminCommand(chatId, messageText);
        }

        // Обрабатываем обычные пользовательские команды
        if (messageText.contains("/")) {
            return commandHandler.handleUserCommand(message, userState);
        }

        // Проверяем, есть ли текущее состояние у пользователя
        if (userState != null) {
            for (StateHandler stateHandler : stateHandlersList.getStateHandlers()) {
                if (stateHandler.canHandle(userState)) {
                    return stateHandler.handleState(chatId, messageText, userState);
                }
            }
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
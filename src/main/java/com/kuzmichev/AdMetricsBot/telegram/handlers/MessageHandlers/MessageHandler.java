package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers;

import com.kuzmichev.AdMetricsBot.config.TelegramConfig;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler.StateHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler.StateHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

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
    TempDataRepository tempDataRepository;
    MessageManagementService messageManagementService;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();

        // Ловим команду отправки сообщения от админа
        if (messageText.contains("/send") && chatId.equals(config.getOwnerId())){
            return sendAllUsersHandler.handleAdminCommand(chatId, messageText);
        }

        // Проверяем, есть ли текущее состояние у пользователя
        String userState = userRepository.getUserStateByChatId(chatId);
        if (userState != null) {
            for (StateHandler stateHandler : stateHandlersList.getStateHandlers()) {
                if (stateHandler.canHandle(userState)) {

                    // Эта секция нужна для удаления старых сообщений
                    int messageId = tempDataRepository.findLastMessageIdByChatId(chatId);
                    // При повторном заходе добавляем в очередь старое сообщение
                    messageManagementService.putMessageToQueue(chatId, messageId);
                    // Удаляем ВСЕ сообщения пользователя из очереди на удаление
                    // Это нужно для того чтобы удалилось и сообщение добавленное при прошлой отправке
                    // Оно добавляется в очередь в методе messageWithoutReturn
                    messageManagementService.deleteMessage(chatId);

                    return stateHandler.handleState(chatId, messageText);
                }
            }
        }

        // Обрабатываем обычные пользовательские команды
        return commandHandler.handleUserCommand(message);
    }
}
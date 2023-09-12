package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.SettingsMenu;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.StartCommandReceived;
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
    StartCommandReceived startCommandReceived;
    SettingsMenu settingsMenu;
    MessageWithoutReturn messageWithoutReturn;
    MessageWithReturn messageWithReturn;
    public BotApiMethod<?> handleUserCommand(Message message) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();
        CommandEnum commandEnum = CommandEnum.fromCommand(messageText);

        if (commandEnum != null) {
            switch (commandEnum) {
                case START -> {
                    return startCommandReceived.sendGreetingMessage(chatId, message.getChat().getFirstName());
                }
                case SETTINGS -> {
                    return messageWithReturn.sendMessage(
                            chatId,
                            BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                            settingsMenu.settingsMenu(chatId),
                            UserStateEnum.SETTINGS_EDIT_STATE);
                }
                case HELP -> {
                    return new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
                }
                case REGISTER -> {
//                    registration.registerUser(chatId, userName);
//                    return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
                }
                case TEST -> {


//                    Тест битрикса
//                    String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
//                    return new SendMessage(chatId, "ЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫ: " + bitrixMainRequest.mainBitrixRequest(projectId));
                }
                default -> {
                    messageWithoutReturn.sendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
                    return new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
                }
            }
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

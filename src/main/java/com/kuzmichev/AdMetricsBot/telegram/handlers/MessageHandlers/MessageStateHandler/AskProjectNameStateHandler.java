package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.AddTokensMenu;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.BackAndExitMenu;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.ProjectManager;
import com.kuzmichev.AdMetricsBot.telegram.utils.UserStateEditor;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;

import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AskProjectNameStateHandler implements StateHandler {
    Validator validator;
    UserStateEditor userStateEditor;
    ProjectManager projectManager;
    MessageWithoutReturn messageWithoutReturn;
    MessageWithReturn messageWithReturn;
    BackAndExitMenu backAndExitMenu;
    AddTokensMenu addTokensMenu;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, UserStateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText) {

        if (validator.validateProjectName(messageText)) {
            userStateEditor.editUserState(chatId, UserStateEnum.SETTINGS_PROJECT_ADD_TOKENS_STATE);
            projectManager.projectCreate(chatId, messageText);
            return messageWithReturn.sendMessage(
                    chatId,
                    BotMessageEnum.ADD_TOKENS_MESSAGE.getMessage(),
                    addTokensMenu.addTokensMenu(chatId),
                    null);
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    BotMessageEnum.PROJECT_NAME_INVALID_MESSAGE.getMessage(),
                    backAndExitMenu.backAndExitMenu(chatId));
            return null;
        }
    }
}

package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.AddInputsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.ProjectManager;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
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
public class AskProjectNameStateHandler implements StateHandler {
    Validator validator;
    UserStateKeeper userStateKeeper;
    ProjectManager projectManager;
    BackAndExitKeyboard backAndExitKeyboard;
    AddInputsKeyboard addInputsKeyboard;

    @Override
    public boolean canHandle(String userState) {
        return userState.equals(StateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName())
                || userState.equals(StateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {

        if (validator.validateProjectName(messageText)) {
            projectManager.projectCreate(chatId, messageText);

            String newUserState;
            if(userState.contains(StateEnum.REGISTRATION.getStateName())) {
                newUserState = StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName();
            } else {
                newUserState = StateEnum.SETTINGS_ADD_INPUTS_STATE.getStateName();
            }
            userStateKeeper.setState(chatId, newUserState);

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.ADD_TOKENS_MESSAGE.getMessage())
                    .replyMarkup(addInputsKeyboard.getKeyboard(newUserState, chatId))
                    .build();

        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.PROJECT_NAME_INVALID_MESSAGE.getMessage())
                    .replyMarkup(backAndExitKeyboard.getKeyboard(userState, chatId))
                    .build();
        }
    }
}


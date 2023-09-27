package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalMessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.AddInputsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
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
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;

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
    BackAndExitKeyboard backAndExitKeyboard;
    AddInputsKeyboard addInputsKeyboard;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, SettingsStateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName())
                || Objects.equals(userStateEnum, RegistrationStateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {

        if (validator.validateProjectName(messageText)) {
            if(Objects.equals(userState, SettingsStateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName())){
                userStateEditor.editState(chatId, SettingsStateEnum.SETTINGS_PROJECT_ADD_TOKENS_STATE.getStateName());
            } else if(Objects.equals(userState, RegistrationStateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName())){
                userStateEditor.editState(chatId, RegistrationStateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName());
            } else userStateEditor.editState(chatId, SettingsStateEnum.WORKING_STATE.getStateName());

            projectManager.projectCreate(chatId, messageText);
            return messageWithReturn.sendMessage(
                    chatId,
                    SettingsMessageEnum.ADD_TOKENS_MESSAGE.getMessage(),
                    addInputsKeyboard.addTokensMenu(userState),
                    null);
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    UniversalMessageEnum.PROJECT_NAME_INVALID_MESSAGE.getMessage(),
                    backAndExitKeyboard.backAndExitMenu(userState));
            return null;
        }
    }
}

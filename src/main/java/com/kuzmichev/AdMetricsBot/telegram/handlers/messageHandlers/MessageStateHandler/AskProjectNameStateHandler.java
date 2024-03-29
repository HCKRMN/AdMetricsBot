package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.Yclients;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.AddInputsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.ProjectManager;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

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
    YclientsRepository yclientsRepository;
    YclientsTestKeyboard yclientsTestKeyboard;
    ProjectsDataTempKeeper projectsDataTempKeeper;

    @Override
    public boolean canHandle(String userState) {
        return userState.equals(StateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName())
                || userState.equals(StateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {

        if (validator.validateProjectName(messageText)) {
            projectManager.projectCreate(chatId, messageText);

            Optional<Yclients> yclients = yclientsRepository.findYclientsByChatIdAndProjectIdIsNull(chatId);

            if (yclients.isPresent()) {
                String projectId = projectsDataTempKeeper.getLastProjectId(chatId);
                yclients.get().setProjectId(projectId);
                yclientsRepository.save(yclients.get());
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(MessageEnum.INPUT_TEST_MESSAGE.getMessage())
                        .replyMarkup(yclientsTestKeyboard.getKeyboard(chatId, userState))
                        .build();
            }

            if(userState.contains(StateEnum.REGISTRATION.getStateName())) {
                userState = StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName();
            } else {
                userState = StateEnum.SETTINGS_ADD_INPUTS_STATE.getStateName();
            }
            userStateKeeper.setState(chatId, userState);

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.ADD_TOKENS_MESSAGE.getMessage())
                    .replyMarkup(addInputsKeyboard.getKeyboard(chatId, userState))
                    .build();

        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.PROJECT_NAME_INVALID_MESSAGE.getMessage())
                    .replyMarkup(backAndExitKeyboard.getKeyboard(chatId, userState))
                    .build();
        }
    }
}


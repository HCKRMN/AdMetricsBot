package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectCreateKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TimeZoneDefinition;
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
public class ManualEditTimeZoneStateHandler implements StateHandler {
    Validator validator;
    BackAndExitKeyboard backAndExitKeyboard;
    TimeZoneDefinition timeZoneDefinition;
    DoneButtonKeyboard doneButtonKeyboard;
    ProjectCreateKeyboard projectCreateKeyboard;
    UserStateKeeper userStateKeeper;

    @Override
    public boolean canHandle(String userState) {
        return userState.equals(StateEnum.SETTINGS_EDIT_TIMEZONE_MANUAL_STATE.getStateName())
                || userState.equals(StateEnum.REGISTRATION_EDIT_TIMEZONE_MANUAL_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {
        if (validator.validateTime(messageText)) {
            timeZoneDefinition.manualTimeZone(chatId, messageText);
            if(userState.contains(StateEnum.REGISTRATION.getStateName())){
                String newUserState = StateEnum.REGISTRATION_EDIT_TIMEZONE_COMPLETE_STATE.getStateName();
                userStateKeeper.setState(chatId, newUserState);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(MessageEnum.REGISTRATION_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage())
                        .replyMarkup(projectCreateKeyboard.getKeyboard(newUserState, chatId))
                        .build();

            } else {
                userStateKeeper.setState(chatId, StateEnum.SETTINGS_EDIT_TIMEZONE_COMPLETE_STATE.getStateName());
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(MessageEnum.SETTINGS_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage())
                        .replyMarkup(doneButtonKeyboard.getKeyboard(userState, chatId))
                        .build();
            }

        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.INVALID_TIME_MESSAGE.getMessage())
                    .replyMarkup(backAndExitKeyboard.getKeyboard(userState, chatId))
                    .build();
        }
    }
}

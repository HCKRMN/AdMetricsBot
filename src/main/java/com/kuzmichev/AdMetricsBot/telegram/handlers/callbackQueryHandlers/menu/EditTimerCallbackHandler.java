package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EditTimerCallbackHandler implements CallbackHandler {
    BackAndExitKeyboard backAndExitKeyboard;
    UserStateKeeper userStateKeeper;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.EDIT_TIMER_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        String newUserState;
        if(userState.contains(StateEnum.REGISTRATION.getStateName())){
            newUserState = StateEnum.REGISTRATION_EDIT_TIMER_STATE.getStateName();
        } else {
            newUserState = StateEnum.SETTINGS_EDIT_TIMER_STATE.getStateName();
        }
        userStateKeeper.setState(chatId, newUserState);

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.ASK_TIME_MESSAGE.getMessage())
                .replyMarkup(backAndExitKeyboard.getKeyboard(chatId, userState))
                .build();
    }
}

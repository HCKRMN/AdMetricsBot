package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.universal;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
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
public class EditTimeZoneCallbackHandler implements CallbackHandler {
    TimeZoneKeyboard timeZoneKeyboard;
    UserStateKeeper userStateKeeper;
    ProjectsDataTempKeeper projectsDataTempKeeper;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.EDIT_TIMEZONE_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (userState.contains(StateEnum.REGISTRATION.getStateName())) {
            userState = StateEnum.REGISTRATION_EDIT_TIMEZONE_STATE.getStateName();
        } else {
            userState = StateEnum.SETTINGS_EDIT_TIMEZONE_STATE.getStateName();
        }

        projectsDataTempKeeper.clearLastProjectId(chatId);
        userStateKeeper.setState(chatId, userState);

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage())
                .replyMarkup(timeZoneKeyboard.getKeyboard(chatId, userState))
                .build();
    }
}
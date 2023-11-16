package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectsKeyboard;
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
public class ProjectsMenuCallbackHandlers  implements CallbackHandler{
    ProjectsKeyboard projectsKeyboard;
    UserStateKeeper userStateKeeper;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.PROJECTS_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        userState = StateEnum.SETTINGS_PROJECTS_STATE.getStateName();
        userStateKeeper.setState(chatId, userState);

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.PROJECT_MENU_MESSAGE.getMessage())
                .replyMarkup(projectsKeyboard.getKeyboard(userState, chatId))
                .build();
    }
}
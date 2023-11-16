package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.service.MetricsMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
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
public class ProjectGetInfoCallbackHandler implements CallbackHandler {
    MetricsMessageBuilder metricsMessageBuilder;
    BackAndExitKeyboard backAndExitKeyboard;
    ProjectsDataTempKeeper projectsDataTempKeeper;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.PROJECT_GET_INFO_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();
        String projectId = projectsDataTempKeeper.getLastProjectId(chatId);

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .parseMode("HTML")
                .text(metricsMessageBuilder.getOneProjectMessage(projectId))
                .replyMarkup(backAndExitKeyboard.getKeyboard(userState, chatId))
                .build();
    }
}

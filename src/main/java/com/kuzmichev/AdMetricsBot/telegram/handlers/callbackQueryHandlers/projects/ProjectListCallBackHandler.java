package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.DynamicCallback;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectListKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectListCallBackHandler implements CallbackHandler {
    ProjectListKeyboard projectListKeyboard;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    CloseButtonKeyboard closeButtonKeyboard;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName())
               || data.contains("page_");
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        String regexPage = "page_.+";
        Map<String, String> pageData = DynamicCallback.handleDynamicCallback(data, regexPage, "page_");
        if (!pageData.isEmpty()) {
            int currentPage = Integer.parseInt(pageData.get("value"));
            projectsDataTempKeeper.setNewPage(chatId, currentPage);
            data = CallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName();
        }

        if (data.equals(CallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName())) {

            return EditMessageText.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .text(MessageEnum.SETTINGS_PROJECTS_LIST_MENU_MESSAGE.getMessage())
                    .replyMarkup(projectListKeyboard.getKeyboard(chatId, userState))
                    .build();
        }

        System.out.println("userState: " + userState);
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.SETTINGS_PROJECTS_LIST_MENU_ERROR_MESSAGE.getMessage())
                .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                .build();
    }
}


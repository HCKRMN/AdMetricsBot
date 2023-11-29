package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.Yclients;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.DynamicCallback;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectSomeKeyboard;
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
import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SomeProjectCallbackHandler  implements CallbackHandler {
    ProjectSomeKeyboard projectSomeKeyboard;
    ProjectRepository projectRepository;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    CloseButtonKeyboard closeButtonKeyboard;
    YclientsRepository yclientsRepository;
    YclientsTestKeyboard yclientsTestKeyboard;

    @Override
    public boolean canHandle(String data) {
        return data.contains("project_");
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();




        String regexProject = "project_.+";
        Map<String, String> projectData = DynamicCallback.handleDynamicCallback(data, regexProject, "project_");
        if (!projectData.isEmpty()) {
            String projectId = projectData.get("value");
            projectsDataTempKeeper.setLastProjectId(chatId, projectId);

            Optional<Yclients> yclients = yclientsRepository.findYclientsByChatIdAndProjectIdIsNull(chatId);
            if (yclients.isPresent()) {
                yclients.get().setProjectId(projectId);
                yclientsRepository.save(yclients.get());
                return EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .text(MessageEnum.INPUT_TEST_MESSAGE.getMessage())
                        .replyMarkup(yclientsTestKeyboard.getKeyboard(chatId, userState))
                        .build();
            }

            return EditMessageText.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .text(projectRepository.findProjectNameByProjectId(projectId))
                    .replyMarkup(projectSomeKeyboard.getKeyboard(chatId, userState))
                    .build();
        }

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.SETTINGS_SOME_PROJECT_MENU_ERROR_MESSAGE.getMessage())
                .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                .build();
    }
}

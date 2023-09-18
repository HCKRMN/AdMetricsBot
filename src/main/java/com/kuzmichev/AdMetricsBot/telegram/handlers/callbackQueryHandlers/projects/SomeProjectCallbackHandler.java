package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.SomeProjectKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.DynamicCallback;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SomeProjectCallbackHandler  implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    SomeProjectKeyboard someProjectKeyboard;
    ProjectRepository projectRepository;
    TempDataRepository tempDataSaver;

    @Override
    public boolean canHandle(String data) {
        return data.contains("project_");
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();
        String projectId = "";

        String regexProject = "project_.+";
        Map<String, String> projectData = DynamicCallback.handleDynamicCallback(data, regexProject, "project_");
        if (!projectData.isEmpty()) {
            projectId = projectData.get("value");
            tempDataSaver.findLastProjectIdByChatId(chatId);
            data = SettingsCallBackEnum.SOME_PROJECT_CALLBACK.getCallBackName();
        }

        if (Objects.equals(data, SettingsCallBackEnum.SOME_PROJECT_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    projectRepository.findProjectNameByProjectId(projectId),
                    null,
                    someProjectKeyboard.someProjectMenu(userState));
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

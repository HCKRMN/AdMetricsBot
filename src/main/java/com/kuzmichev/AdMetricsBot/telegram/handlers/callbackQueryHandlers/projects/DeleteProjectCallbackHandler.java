package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DeleteProjectKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DeleteProjectCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    DeleteProjectKeyboard deleteProjectKeyboard;
    TempDataRepository tempDataRepository;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    DoneButtonKeyboard doneButtonKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();
        String projectId;

        if (Objects.equals(data, SettingsCallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.PROJECT_DELETE_STEP_1_MESSAGE.getMessage(),
                    null,
                    deleteProjectKeyboard.deleteProjectMenu());
        } else if(Objects.equals(data, SettingsCallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName())) {
            projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            projectRepository.removeProjectByProjectId(projectId);
            userRepository.decrementProjectsCount(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.PROJECT_DELETE_STEP_2_MESSAGE.getMessage(),
                    null,
                    doneButtonKeyboard.doneButtonMenu()
            );
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
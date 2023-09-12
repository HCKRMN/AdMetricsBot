package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Projects;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.DeleteProjectMenu;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.DoneButtonMenu;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
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
    DeleteProjectMenu deleteProjectMenu;
    TempDataRepository tempDataRepository;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    DoneButtonMenu doneButtonMenu;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();
        String projectId;

        if (Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.PROJECT_DELETE_STEP_1_MESSAGE.getMessage(),
                    null,
                    deleteProjectMenu.deleteProjectMenu());
        } else if(Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName())) {
            projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            projectRepository.removeProjectByProjectId(projectId);
            userRepository.decrementProjectsCount(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.PROJECT_DELETE_STEP_2_MESSAGE.getMessage(),
                    null,
                    doneButtonMenu.done()
            );
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
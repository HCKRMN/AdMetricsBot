package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectDeleteKeyboard;
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
    ProjectDeleteKeyboard projectDeleteKeyboard;
    TempDataRepository tempDataRepository;
    ProjectRepository projectRepository;
    YandexRepository yandexRepository;
    BitrixRepository bitrixRepository;
    YclientsRepository yclientsRepository;
    UserRepository userRepository;
    DoneButtonKeyboard doneButtonKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();
        String projectId;

        if (Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.PROJECT_DELETE_STEP_1_MESSAGE.getMessage(),
                    null,
                    projectDeleteKeyboard.deleteProjectMenu());
        } else if(Objects.equals(data, CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName())) {
            projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            projectRepository.removeProjectByProjectId(projectId);
            yandexRepository.removeYandexByProjectId(projectId);
            bitrixRepository.removeBitrixByProjectId(projectId);
            yclientsRepository.removeYclientsByProjectId(projectId);

            userRepository.decrementProjectsCount(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.PROJECT_DELETE_STEP_2_MESSAGE.getMessage(),
                    null,
                    doneButtonKeyboard.doneButtonMenu()
            );
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
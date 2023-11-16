package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.delete;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
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
public class DeleteProjectStepTwoCallbackHandler implements CallbackHandler {
    ProjectRepository projectRepository;
    YandexRepository yandexRepository;
    BitrixRepository bitrixRepository;
    YclientsRepository yclientsRepository;
    DoneButtonKeyboard doneButtonKeyboard;
    ProjectsDataTempKeeper tempDataRepository;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        String projectId = tempDataRepository.getLastProjectId(chatId);
        projectRepository.removeProjectByProjectId(projectId);
        yandexRepository.removeYandexByProjectId(projectId);
        bitrixRepository.removeBitrixByProjectId(projectId);
        yclientsRepository.removeYclientsByProjectId(projectId);

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.PROJECT_DELETE_STEP_2_MESSAGE.getMessage())
                .replyMarkup(doneButtonKeyboard.getKeyboard(userState, chatId))
                .build();
    }
}
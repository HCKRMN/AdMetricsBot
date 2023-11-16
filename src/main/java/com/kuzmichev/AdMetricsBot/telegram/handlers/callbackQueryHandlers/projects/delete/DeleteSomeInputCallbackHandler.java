package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.delete;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.DynamicCallback;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DeleteInputsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.InputsManager;
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
public class DeleteSomeInputCallbackHandler implements CallbackHandler {
    DeleteInputsKeyboard deleteInputsKeyboard;
    InputsManager inputsManager;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    CloseButtonKeyboard closeButtonKeyboard;

    @Override
    public boolean canHandle(String data) {
        return data.contains(CallBackEnum.INPUT_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        String inputName;
        String projectId;

        String regexInput = "input_.+";
        Map<String, String> inputData = DynamicCallback.handleDynamicCallback(data, regexInput, "input_");
        if (!inputData.isEmpty()) {
            inputName = inputData.get("value");
            projectId = projectsDataTempKeeper.getLastProjectId(chatId);
            inputsManager.deleteInputs(projectId, inputName);

            return EditMessageText.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .text(MessageEnum.PROJECT_INPUT_DELETE_SUCCESS_MESSAGE.getMessage())
                    .replyMarkup(deleteInputsKeyboard.deleteInputsMenu(chatId, userState))
                    .build();
        }

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.PROJECT_INPUT_DELETE_ERROR_MESSAGE.getMessage())
                .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                .build();
    }
}
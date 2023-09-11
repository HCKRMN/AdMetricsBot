package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Projects;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.DynamicCallback;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.InputsManager;
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
public class DeleteInputCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    InlineKeyboards inlineKeyboards;
    TempDataRepository tempDataRepository;
    InputsManager inputsManager;

    @Override
    public boolean canHandle(String data) {
        return data.contains("input_");
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();
        String inputName = "";
        String projectId;

        String regexInput = "input_.+";
        Map<String, String> inputData = DynamicCallback.handleDynamicCallback(data, regexInput, "input_");
        if (!inputData.isEmpty()) {
            inputName = inputData.get("value");
            data = CallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName();
        }

        if (Objects.equals(data, CallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName())) {
            if (inputName != null){
                projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
                inputsManager.deleteInputs(projectId, inputName);
            }
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.PROJECT_INPUT_DELETE_MESSAGE.getMessage(),
                    null,
                    inlineKeyboards.deleteInputsMenu(chatId));
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
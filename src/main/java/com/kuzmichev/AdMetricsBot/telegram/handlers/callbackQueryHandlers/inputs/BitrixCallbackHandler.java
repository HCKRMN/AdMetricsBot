package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMainRequest;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BitrixTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
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
public class BitrixCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    BackAndExitKeyboard backAndExitKeyboard;
    TempDataRepository tempDataRepository;
    BitrixMainRequest bitrixMainRequest;
    BitrixTestKeyboard bitrixTestKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.TEST_BITRIX_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, SettingsCallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.ADD_BITRIX_STEP_1_MESSAGE.getMessage(),
                    SettingsStateEnum.SETTINGS_PROJECT_ADD_BITRIX_STATE.getStateName(),
                    backAndExitKeyboard.backAndExitMenu(userState));
        }
        if (Objects.equals(data, SettingsCallBackEnum.TEST_BITRIX_CALLBACK.getCallBackName())) {
            String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    bitrixMainRequest.bitrixMainRequest(projectId),
                    null,
                    bitrixTestKeyboard.bitrixTestMenu(userState));
        }

        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

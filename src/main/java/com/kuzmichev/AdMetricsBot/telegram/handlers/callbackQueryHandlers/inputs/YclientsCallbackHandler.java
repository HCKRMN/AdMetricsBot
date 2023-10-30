package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.service.yclients.YclientsMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.replyKeyboards.ReplyKeyboardMaker;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
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
public class YclientsCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    BackAndExitKeyboard backAndExitKeyboard;
    TempDataRepository tempDataRepository;
    YclientsMessageBuilder yclientsMessageBuilder;
    YclientsTestKeyboard yclientsTestKeyboard;
    TempDataSaver tempDataSaver;
    ReplyKeyboardMaker replyKeyboardMaker;
    MessageManagementService messageManagementService;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.TEST_YCLIENTS_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName())) {

            String newState = StateEnum.SETTINGS_PROJECT_ADD_YCLIENTS_STATE.getStateName();
            if(userState.equals(StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
                newState = StateEnum.REGISTRATION_ADD_YCLIENTS_STATE.getStateName();
            }

            messageManagementService.putMessageToQueue(chatId, messageId);
            messageManagementService.deleteMessage(chatId);

            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.sendMessage(
                    chatId,
                    MessageEnum.ADD_YCLIENTS_STEP_1_MESSAGE.getMessage(),
                    backAndExitKeyboard.backAndExitMenu(userState),
                    replyKeyboardMaker.getContactKeyboard(),
                    newState);
        }

        if (Objects.equals(data, CallBackEnum.TEST_YCLIENTS_CALLBACK.getCallBackName())) {
            String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);

            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    yclientsMessageBuilder.getMessage(projectId),
                    null,
                    yclientsTestKeyboard.yclientsTestMenu(userState));
        }

        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

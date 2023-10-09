package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.service.yandex.YandexMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YandexAddKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YandexTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
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
public class YandexCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    YandexTestKeyboard yandexTestKeyboard;
    YandexMessageBuilder yandexMessageBuilder;
    YandexAddKeyboard yandexAddKeyboard;
    TempDataRepository tempDataRepository;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
//            String newState = StateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE.getStateName();
//            if (userState.equals(StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
//                newState = StateEnum.REGISTRATION_PROJECT_ADD_YANDEX_STATE.getStateName();
//            }
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.ADD_YANDEX_MESSAGE.getMessage(),
//                    newState
                    null,
                    yandexAddKeyboard.addYandexMenu(chatId, userState));
        } else if (Objects.equals(data, CallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName())) {
            String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    yandexMessageBuilder.getMessage(projectId),
                    null,
                    yandexTestKeyboard.yandexTestMenu(userState));
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

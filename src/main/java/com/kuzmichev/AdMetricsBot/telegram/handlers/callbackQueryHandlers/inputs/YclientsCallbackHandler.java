package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsAddKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsCallbackHandler implements CallbackHandler {
    MessageManagementService messageManagementService;
    UserStateKeeper userStateKeeper;
    YclientsAddKeyboard yclientsAddKeyboard;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();

        String newState;
        if(userState.equals(StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
            newState = StateEnum.REGISTRATION_ADD_YCLIENTS_STATE.getStateName();
        } else {
            newState = StateEnum.SETTINGS_ADD_YCLIENTS_STATE.getStateName();
        }
        userStateKeeper.setState(chatId, newState);

        System.out.println("Получаем последнее сообщение из YclientsCallbackHandler");
        int lastMessageId = messageManagementService.getLastMessageId(chatId);

        System.out.println("Удаляем сообщения из YclientsCallbackHandler");
        messageManagementService.deleteMessage(chatId);
        int nextMessageId = lastMessageId+1;
        System.out.println("Кладем в очередь из YclientsCallbackHandler, которое сейчас отправится: " + nextMessageId);
        messageManagementService.putMessageToQueue(chatId, nextMessageId);

        return SendMessage.builder()
                .chatId(chatId)
                .text(MessageEnum.ADD_YCLIENTS_MESSAGE.getMessage())
                .replyMarkup(yclientsAddKeyboard.getKeyboard(userState, chatId))
                .build();
    }
}

package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BitrixTestKeyboard;
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
public class BitrixTestCallbackHandler implements CallbackHandler {
    BitrixMessageBuilder bitrixMessageBuilder;
    BitrixTestKeyboard bitrixTestKeyboard;
    ProjectsDataTempKeeper projectsDataTempKeeper;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.TEST_BITRIX_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        String projectId = projectsDataTempKeeper.getLastProjectId(chatId);

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .parseMode("HTML")
                .text(bitrixMessageBuilder.getMessage(projectId))
                .replyMarkup(bitrixTestKeyboard.bitrixTestMenu(userState))
                .build();
    }
}

package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixDomainStateHandler implements StateHandler {
    Validator validator;
    MessageWithoutReturn messageWithoutReturn;
    InlineKeyboards inlineKeyboards;
    BitrixRepository bitrixRepository;
    TempDataRepository tempDataRepository;
    MessageWithReturn messageWithReturn;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, UserStateEnum.SETTINGS_PROJECT_ADD_BITRIX_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText) {

        if (validator.validateBitrixDomain(messageText)) {
            Bitrix bitrix = new Bitrix();
            bitrix.setBitrixDomain(messageText);
            bitrix.setChatId(chatId);
            bitrix.setProjectId(tempDataRepository.findLastProjectIdByChatId(chatId));
            bitrixRepository.save(bitrix);
            return messageWithReturn.sendMessage(
                    chatId,
                    BotMessageEnum.ADD_BITRIX_STEP_2_MESSAGE.getMessage(),
                    inlineKeyboards.bitrixLinkMenu(chatId),
                    null);
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    BotMessageEnum.INVALID_BITRIXDOMAIN_MESSAGE.getMessage(),
                    inlineKeyboards.backAndExitMenu(chatId));
            return null;
        }
    }
}

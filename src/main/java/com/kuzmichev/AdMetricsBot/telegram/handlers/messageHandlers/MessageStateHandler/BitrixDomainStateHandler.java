package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BitrixAddKeyboard;
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
    BitrixAddKeyboard bitrixAddKeyboard;
    BackAndExitKeyboard backAndExitKeyboard;
    BitrixRepository bitrixRepository;
    TempDataRepository tempDataRepository;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, StateEnum.SETTINGS_PROJECT_ADD_BITRIX_STATE.getStateName())
                || Objects.equals(userStateEnum, StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {
        if (validator.validateBitrixDomain(messageText)) {
            String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            Bitrix bitrix = new Bitrix();
            bitrix.setBitrixDomain(messageText);
            bitrix.setChatId(chatId);
            bitrix.setProjectId(projectId);
            bitrixRepository.save(bitrix);
            messageWithoutReturn.sendMessage(
                    chatId,
                    MessageEnum.ADD_BITRIX_STEP_2_MESSAGE.getMessage(),
                    bitrixAddKeyboard.bitrixLinkMenu(chatId, projectId, userState));
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    MessageEnum.INVALID_BITRIXDOMAIN_MESSAGE.getMessage(),
                    backAndExitKeyboard.backAndExitMenu(userState));
        }
        return null;
    }
}

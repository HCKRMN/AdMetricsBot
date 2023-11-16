package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BitrixAddKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixDomainStateHandler implements StateHandler {
    Validator validator;
    BitrixAddKeyboard bitrixAddKeyboard;
    BackAndExitKeyboard backAndExitKeyboard;
    BitrixRepository bitrixRepository;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    UserStateKeeper userStateKeeper;

    @Override
    public boolean canHandle(String userStateEnum) {
        return userStateEnum.equals(StateEnum.SETTINGS_ADD_BITRIX_STATE.getStateName())
                || userStateEnum.equals(StateEnum.REGISTRATION_ADD_BITRIX_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {
        if (validator.validateBitrixDomain(messageText)) {
            String projectId = projectsDataTempKeeper.getLastProjectId(chatId);
            Bitrix bitrix = new Bitrix();
            bitrix.setBitrixDomain(messageText);
            bitrix.setChatId(chatId);
            bitrix.setProjectId(projectId);
            bitrixRepository.save(bitrix);

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.ADD_BITRIX_STEP_2_MESSAGE.getMessage())
                    .replyMarkup(bitrixAddKeyboard.bitrixLinkMenu(chatId, projectId, userState))
                    .build();

        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.INVALID_BITRIX_DOMAIN_MESSAGE.getMessage())
                    .replyMarkup(backAndExitKeyboard.getKeyboard(userState, chatId))
                    .build();
        }
    }
}

package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SomeProjectMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitMenu backAndExitMenu;
    public InlineKeyboardMarkup someProjectMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECT_ADD_TOKEN_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECT_DELETE_TOKEN_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECT_GET_INFO_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_GET_INFO_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECT_DELETE_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName(),
                                        null

                                )
                        ),
                        backAndExitMenu.backAndExitMenuButtons(chatId)
                )
        );
    }
}

package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.InlineKeyboardMaker;
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
public class ProjectSomeKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup someProjectMenu(String userState) {
        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.PROJECT_ADD_TOKEN_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.PROJECT_DELETE_TOKEN_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.PROJECT_GET_INFO_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.PROJECT_GET_INFO_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.PROJECT_DELETE_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName(),
                                        null

                                )
                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
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
public class ProjectCreateKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup projectCreateKeyboard(String userState) {

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRow(
                        inlineKeyboardMaker.addButton(
                                ButtonEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                                CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName(),
                                null
                        )
                ), backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

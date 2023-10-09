package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
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
public class ProjectDeleteKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    public InlineKeyboardMarkup deleteProjectMenu() {
        return inlineKeyboardMaker.addMarkup(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    ButtonEnum.DELETE_PROJECT_BUTTON.getButtonName(),
                                    CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName(),
                                    null
                            ),
                            inlineKeyboardMaker.addButton(
                                    ButtonEnum.CANCEL_BUTTON.getButtonName(),
                                    CallBackEnum.NOT_DELETE_PROJECT_CALLBACK.getCallBackName(),
                                    null
                            )
                    )
        );
    }
}

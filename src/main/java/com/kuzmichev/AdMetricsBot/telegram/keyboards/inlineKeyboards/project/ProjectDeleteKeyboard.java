package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
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
                                    SettingsButtonEnum.DELETE_PROJECT_BUTTON.getButtonName(),
                                    SettingsCallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName(),
                                    null
                            ),
                            inlineKeyboardMaker.addButton(
                                    SettingsButtonEnum.CANCEL_BUTTON.getButtonName(),
                                    SettingsCallBackEnum.NOT_DELETE_PROJECT_CALLBACK.getCallBackName(),
                                    null
                            )
                    )
        );
    }
}

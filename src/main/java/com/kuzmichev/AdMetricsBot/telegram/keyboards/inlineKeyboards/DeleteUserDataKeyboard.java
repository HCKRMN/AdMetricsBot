package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
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
public class DeleteUserDataKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    public InlineKeyboardMarkup deleteUserDataMenu() {
        return inlineKeyboardMaker.addMarkup(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    SettingsButtonEnum.DELETE_USER_DATA_BUTTON.getButtonName(),
                                    SettingsCallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName(),
                                    null
                            ),
                            inlineKeyboardMaker.addButton(
                                    SettingsButtonEnum.NOT_DELETE_USER_DATA_BUTTON.getButtonName(),
                                    SettingsCallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName(),
                                    null
                            )
                    )
        );

    }
}

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
public class DeleteUserDataMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    public InlineKeyboardMarkup deleteUserDataMenu() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.DELETE_USER_DATA_BUTTON.getButtonName(),
                                        CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName(),
                                        null
                                ),
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.NOT_DELETE_USER_DATA_BUTTON.getButtonName(),
                                        CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );

    }
}

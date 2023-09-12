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
public class DeleteProjectMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    public InlineKeyboardMarkup deleteProjectMenu() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.DELETE_PROJECT_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_DELETE_STEP_2_CALLBACK.getCallBackName(),
                                        null
                                ),
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.CANCEL_BUTTON.getButtonName(),
                                        CallBackEnum.NOT_DELETE_PROJECT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }
}

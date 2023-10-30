package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
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
public class YclientsTestKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup yclientsTestMenu(String userState) {
        String callBack = CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName();
        if (userState.equals(StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
            callBack = CallBackEnum.UNIVERSAL_EDIT_TIMER_CALLBACK.getCallBackName();
        }

        return inlineKeyboardMaker.addMarkup(
                        //Кнопка тестового запроса
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.TEST_INPUTS_BUTTON.getButtonName(),
                                        CallBackEnum.TEST_YCLIENTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка добавить другие токены
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка продолжить
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.UNIVERSAL_CONTINUE_BUTTON.getButtonName(),
                                        callBack,
                                        null
                                )
                        )

        );
    }
}
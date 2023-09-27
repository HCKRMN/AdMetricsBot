package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalCallbackEnum;
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
public class BitrixTestKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup bitrixTestMenu(String userState) {
        String callBack = SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName();
        if (userState.equals(RegistrationStateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
            callBack = UniversalCallbackEnum.UNIVERSAL_EDIT_TIMER_CALLBACK.getCallBackName();
        }

        return inlineKeyboardMaker.addMarkup(
                        //Кнопка тестового запроса
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        UniversalButtonEnum.TEST_INPUTS_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.TEST_BITRIX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка добавить другие токены
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка продолжить
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        UniversalButtonEnum.UNIVERSAL_CONTINUE_BUTTON.getButtonName(),
                                        callBack,
                                        null
                                )
                        )

        );
    }
}
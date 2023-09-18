package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationCallbackEnum;
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
public class StartRegistrationKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup startRegistrationKeyboard() {
        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        RegistrationButtonEnum.REGISTRATION_BUTTON.getButtonName(),
                                        RegistrationCallbackEnum.START_REGISTRATION_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
        );
    }
}
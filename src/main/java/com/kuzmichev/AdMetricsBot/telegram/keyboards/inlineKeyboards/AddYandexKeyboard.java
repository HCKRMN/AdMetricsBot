package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddYandex;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddYandexKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    AddYandex addYandex;
    BackAndExitKeyboard backAndExitKeyboard;

    public InlineKeyboardMarkup addYandexMenu(String chatId, String userState) {
        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.YANDEX_ADD_TOKEN_LINK_BUTTON.getButtonName(),
                                        null,
                                        addYandex.getYandexAuthorizationUrl(chatId, userState)
                                )
//                                        на всякий случай оставлю кнопку получения доступа к апи, вроде как ненужно, но пусть будет
//                                        ,
//                                        inlineKeyboardMaker.addButton(
//                                                SettingsButtonEnum.YANDEX_API_SETTINGS_BUTTON.getButtonName(),
//                                                null,
//                                                addYandex.getApiSettingsUrl(chatId)
//                                        )
                        ),
                        //Старая кнопка тестового запроса, пока что убрана чтобы пользователь не мог нажать без регистрации
//                        inlineKeyboardMaker.addRow(
//                                inlineKeyboardMaker.addButton(
//                                        SettingsButtonEnum.TEST_YANDEX_BUTTON.getButtonName(),
//                                        SettingsCallBackEnum.TEST_MESSAGE_CALLBACK,
//                                        null
//                                )
//                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

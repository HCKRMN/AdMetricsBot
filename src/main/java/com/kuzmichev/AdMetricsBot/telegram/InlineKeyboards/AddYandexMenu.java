package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddYandex;
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
public class AddYandexMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    AddYandex addYandex;
    BackAndExitMenu backAndExitMenu;

    public InlineKeyboardMarkup addYandexMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.YANDEX_ADD_TOKEN_LINK_BUTTON.getButtonName(),
                                        null,
                                        addYandex.getYandexAuthorizationUrl(chatId)
                                )
//                                        на всякий случай оставлю кнопку получения доступа к апи, вроде как ненужно, но пусть будет
//                                        ,
//                                        inlineKeyboardMaker.addButton(
//                                                ButtonNameEnum.YANDEX_API_SETTINGS_BUTTON.getButtonName(),
//                                                null,
//                                                addYandex.getApiSettingsUrl(chatId)
//                                        )
                        ),
                        //Старая кнопка тестового запроса, пока что убрана чтобы пользователь не мог нажать без регистрации
//                        inlineKeyboardMaker.addRow(
//                                inlineKeyboardMaker.addButton(
//                                        ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
//                                        CallBackEnum.TEST_MESSAGE_CALLBACK,
//                                        null
//                                )
//                        ),
                        backAndExitMenu.backAndExitMenuButtons(chatId)
                )
        );
    }
}

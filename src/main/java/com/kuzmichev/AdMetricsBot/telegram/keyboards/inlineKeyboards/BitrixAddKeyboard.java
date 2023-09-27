package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalButtonEnum;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddBitrix;
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
public class BitrixAddKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    AddBitrix addBitrix;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup bitrixLinkMenu(String chatId, String projectId, String userState) {
        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                // Ссылка на получения токена битрикс
                                inlineKeyboardMaker.addButton(
                                        UniversalButtonEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        addBitrix.getBitrixAuthorizationUrl(chatId, projectId, userState)
                                )
                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

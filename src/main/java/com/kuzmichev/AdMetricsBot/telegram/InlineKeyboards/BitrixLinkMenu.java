package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
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
public class BitrixLinkMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    AddBitrix addBitrix;
    BackAndExitMenu backAndExitMenu;
    public InlineKeyboardMarkup bitrixLinkMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Ссылка на получения токена битрикс
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        addBitrix.getBitrixAuthorizationUrl(chatId)
                                )

                        ),
                        backAndExitMenu.backAndExitMenuButtons(chatId)
                )
        );
    }
}

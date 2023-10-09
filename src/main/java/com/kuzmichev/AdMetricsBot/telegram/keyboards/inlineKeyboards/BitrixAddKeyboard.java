package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixAuthUrl;
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
    BitrixAuthUrl bitrixAuthUrl;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup bitrixLinkMenu(String chatId, String projectId, String userState) {
        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                // Ссылка на получения токена битрикс
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        bitrixAuthUrl.getBitrixAuthorizationUrl(chatId, projectId, userState)
                                )
                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}

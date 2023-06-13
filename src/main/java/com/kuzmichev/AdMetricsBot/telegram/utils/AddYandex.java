package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddYandex {
    final InlineKeyboardMaker inlineKeyboardMaker;
    @Value("${yaClientID}")
    String clientId;
    @Value("${telegram.webhook-path}")
    String link;
    @Value("${yaRedirectURI}")
    String redirectUri;

    public SendMessage addYaData(String chatId) {

        String yaAuthorizationUrl = "https://oauth.yandex.ru/authorize" +
                "?response_type=token" +
                "&client_id=" + clientId +
                "&redirect_uri=" + link + redirectUri +
                "&state=" + chatId;

        String apiSettingsUrl = "https://direct.yandex.ru/registered/main.pl?cmd=apiSettings";

        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADD_YANDEX_MESSAGE.getMessage());

        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.YANDEX_ADD_TOKEN_LINK_BUTTON.getButtonName(),
                                                null,
                                                yaAuthorizationUrl
                                        )
//                                        на всякий случай оставлю кнопку получения доступа к апи, вроде как ненужно, но пусть будет
//                                        ,
//                                        inlineKeyboardMaker.addButton(
//                                                ButtonNameEnum.YANDEX_API_SETTINGS_BUTTON.getButtonName(),
//                                                null,
//                                                apiSettingsUrl
//                                        )
                                ),
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
                                                "TEST_YA",
                                                null
                                        ),
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.CONTINUE_BUTTON.getButtonName(),
                                                "NO_CONTINUE",
                                                null
                                        )
                                )
                        )
                )
        );



//        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//        rowsInLine.add(inlineKeyboardMaker.getButton(
//                ButtonNameEnum.LINK_BUTTON.getButtonName(),
//                null,
//                yaAuthorizationUrl)
//        );
//        rowsInLine.add(inlineKeyboardMaker.getButton(
//                ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
//                "TEST_YA",
//                null));
//        rowsInLine.add(inlineKeyboardMaker.getButton(
//                ButtonNameEnum.CONTINUE_BUTTON.getButtonName(),
//                "NO_CONTINUE",
//                null));
//        markupInLine.setKeyboard(rowsInLine);
//        sendMessage.setReplyMarkup(markupInLine);

        log.info("Пользователь id: {} установил временную зону.", chatId);
        return sendMessage;


    }


}

package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.service.YandexDirectRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddYandex {
    final YandexRepository yandexRepository;
    @Value("${yandexClientID}")
    String clientId;
    @Value("${telegram.webhook-path}")
    String link;
    @Value("${yandexRedirectURI}")
    String redirectUri;


    public String getYandexAuthorizationUrl(String chatId) {
        return "https://oauth.yandex.ru/authorize" +
                "?response_type=token" +
                "&client_id=" + clientId +
                "&redirect_uri=" + link + redirectUri +
                "&state=" + chatId;
    }
    public String getApiSettingsUrl(String chatId) {
        return "https://direct.yandex.ru/registered/main.pl?cmd=apiSettings";
    }

    public String testYandex(String chatId) {
        try {
            String result = YandexDirectRequest.ya(yandexRepository, chatId);
            System.out.println(result);
            if (result.equals("-1")) {
                return BotMessageEnum.YANDEX_ERROR_GET_RESULT_MESSAGE.getMessage();
            } else {
                return BotMessageEnum.YANDEX_RESULT_MESSAGE.getMessage() + result;
            }
        } catch (Exception e) {
            return BotMessageEnum.YANDEX_ERROR_GET_TOKEN_MESSAGE.getMessage();
        }
    }





//    public SendMessage addYaData(String chatId) {
//
//        String yaAuthorizationUrl = "https://oauth.yandex.ru/authorize" +
//                "?response_type=token" +
//                "&client_id=" + clientId +
//                "&redirect_uri=" + link + redirectUri +
//                "&state=" + chatId;
//
//        String apiSettingsUrl = "https://direct.yandex.ru/registered/main.pl?cmd=apiSettings";
//
//        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADD_YANDEX_MESSAGE.getMessage());
//
//        sendMessage.setReplyMarkup(inlineKeyboards.addYandexMenu(chatId, yaAuthorizationUrl, apiSettingsUrl));
////                inlineKeyboardMaker.addMarkup(
////                        inlineKeyboardMaker.addRows(
////                                inlineKeyboardMaker.addRow(
////                                        inlineKeyboardMaker.addButton(
////                                                ButtonNameEnum.YANDEX_ADD_TOKEN_LINK_BUTTON.getButtonName(),
////                                                null,
////                                                yaAuthorizationUrl
////                                        )
//////                                        на всякий случай оставлю кнопку получения доступа к апи, вроде как ненужно, но пусть будет
//////                                        ,
//////                                        inlineKeyboardMaker.addButton(
//////                                                ButtonNameEnum.YANDEX_API_SETTINGS_BUTTON.getButtonName(),
//////                                                null,
//////                                                apiSettingsUrl
//////                                        )
////                                ),
////                                inlineKeyboardMaker.addRow(
////                                        inlineKeyboardMaker.addButton(
////                                                ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
////                                                CallBackEnum.TEST_MESSAGE_CALLBACK,
////                                                null
////                                        ),
////                                        inlineKeyboardMaker.addButton(
////                                                ButtonNameEnum.CONTINUE_BUTTON.getButtonName(),
////                                                CallBackEnum.NO_CONTINUE_CALLBACK,
////                                                null
////                                        )
////                                )
////                        )
////                )
////        );
//        log.info("Пользователь id: {} добавил данные Яндекс.", chatId);
//        return sendMessage;
//    }


}

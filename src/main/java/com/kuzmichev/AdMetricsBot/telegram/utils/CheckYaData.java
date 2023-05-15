package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.model.YaData;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CheckYaData {
    AddYandex addYandex;
    YaRepository yaRepository;
    InlineKeyboardMaker inlineKeyboardMaker;

    public SendMessage findYaData(String chatId) {
        YaData yaData = yaRepository.findById(chatId).orElse(null);
        if (yaData != null && yaData.getYaToken() != null) {

            SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ACCOUNT_ALREADY_ADDED_MESSAGE.getMessage());
            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
            rowsInLine.add(inlineKeyboardMaker.getButton(
                    ButtonNameEnum.YES_ADD_BUTTON.getButtonName(),
                    "YES_ADD",
                    null));
            rowsInLine.add(inlineKeyboardMaker.getButton(
                    ButtonNameEnum.NO_CONTINUE_BUTTON.getButtonName(),
                    "NO_CONTINUE",
                    null));

            markupInLine.setKeyboard(rowsInLine);
            sendMessage.setReplyMarkup(markupInLine);

            log.info("Пользователь id: {} Уже добавлял Яндекс.", chatId);
            return sendMessage;
        } else {
            return addYandex.addYaData(chatId);
//            addYaData(chatId);
//            preTestYaData(chatId);
        }
    }

}

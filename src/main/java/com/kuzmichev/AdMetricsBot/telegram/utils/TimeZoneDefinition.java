package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.config.TelegramConfig;
import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStatesEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
public class TimeZoneDefinition {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final UserRepository userRepository;
    @Value("${telegram.webhook-path}")
    String link;

    public SendMessage requestTimeZoneSettingLink(String chatId) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage());


        String state = userRepository.getUserStateByChatId(chatId);

        // Создаем пустой список для кнопок
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        // Добавляем кнопку "Continue" всегда
        buttonsRow.add(inlineKeyboardMaker.addButton(
                ButtonNameEnum.LINK_BUTTON.getButtonName(),
                CallBackEnum.ADD_ACCOUNTS_CALLBACK,
                ipToTimeZoneLink
        ));

        // Добавляем кнопку "Continue" только если условие выполняется
        if (state.equals(UserStatesEnum.REGISTRATION_STATE.getStateName())) {
            buttonsRow.add(inlineKeyboardMaker.addButton(
                    ButtonNameEnum.CONTINUE_BUTTON.getButtonName(),
                    CallBackEnum.ADD_ACCOUNTS_CALLBACK,
                    null
            ));
        }

        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(buttonsRow)
                )
        );

        log.info("Пользователь id: {} установил временную зону.", chatId);
        return sendMessage;
    }
}

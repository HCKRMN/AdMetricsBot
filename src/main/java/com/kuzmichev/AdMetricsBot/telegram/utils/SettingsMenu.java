package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SettingsMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    ScheduledMessageRepository scheduledMessageRepository;

    /**
     * Создает сообщение с настройками меню.
     *
     * @param chatId идентификатор чата
     * @return сообщение с настройками меню
     */
    public SendMessage menuMaker(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage());

        String launchButton;
        CallBackEnum launchCallback;
        boolean state = scheduledMessageRepository.findEnableSendingMessagesByChatId(chatId);
        if (state) {
            launchButton = ButtonNameEnum.SETTINGS_DISABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = CallBackEnum.DISABLE_NOTIFICATIONS_CALLBACK;
        } else {
            launchButton = ButtonNameEnum.SETTINGS_ENABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = CallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK;
        }

        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        // Кнопка создания проекта
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                                                CallBackEnum.PROJECT_CREATE_CALLBACK,
                                                null
                                        ),
                                        // Кнопка управления существующими проектами
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.PROJECTS_BUTTON.getButtonName(),
                                                CallBackEnum.PROJECTS_CALLBACK,
                                                null
                                        )
                                ),
                                inlineKeyboardMaker.addRow(
                                        // Кнопка включения/выключения рассылки
                                        inlineKeyboardMaker.addButton(
                                                launchButton,
                                                launchCallback,
                                                null
                                        ),
                                        // Кнопка настроек часового пояса
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_EDIT_TIMEZONE_BUTTON.getButtonName(),
                                                CallBackEnum.EDIT_TIMEZONE_CALLBACK,
                                                null
                                        )
                                ),
                                inlineKeyboardMaker.addRow(
                                        // Кнопка настроек языка
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_EDIT_LANGUAGE_BUTTON.getButtonName(),
                                                CallBackEnum.EDIT_LANGUAGE_CALLBACK,
                                                null
                                        ),
                                        // Кнопка настроек таймера
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_EDIT_TIMER_BUTTON.getButtonName(),
                                                CallBackEnum.EDIT_TIMER_CALLBACK,
                                                null
                                        )
                                ),
                                inlineKeyboardMaker.addRow(
                                        // Кнопка добавления аккаунтов
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                                CallBackEnum.ADD_ACCOUNTS_CALLBACK,
                                                null
                                        ),
                                        // Кнопка удаления данных пользователя
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_DELETE_USER_BUTTON.getButtonName(),
                                                CallBackEnum.DELETE_USER_STEP_1_CALLBACK,
                                                null
                                        )
                                )
                        )
                )
        );
        return sendMessage;
    }
}

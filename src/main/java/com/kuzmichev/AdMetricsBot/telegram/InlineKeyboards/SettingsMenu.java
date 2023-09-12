package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
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
public class SettingsMenu {
    ScheduledMessageRepository scheduledMessageRepository;
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup settingsMenu(String chatId) {

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

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка управления проектами
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECTS_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECTS_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка включения/выключения рассылки
                                inlineKeyboardMaker.addButton(
                                        launchButton,
                                        launchCallback.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка настроек часового пояса
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EDIT_TIMEZONE_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_TIMEZONE_LINK_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка настроек таймера
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EDIT_TIMER_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_TIMER_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка настроек языка
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EDIT_LANGUAGE_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_LANGUAGE_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка удаления данных пользователя
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_DELETE_USER_BUTTON.getButtonName(),
                                        CallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка выхода из меню
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }
}

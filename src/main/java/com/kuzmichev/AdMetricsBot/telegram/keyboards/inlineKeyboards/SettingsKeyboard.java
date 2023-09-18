package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
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
public class SettingsKeyboard {
    ScheduledMessageRepository scheduledMessageRepository;
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup settingsMenu(String chatId) {

        String launchButton;
        SettingsCallBackEnum launchCallback;
        boolean state = scheduledMessageRepository.findEnableSendingMessagesByChatId(chatId);
        if (state) {
            launchButton = SettingsButtonEnum.SETTINGS_DISABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = SettingsCallBackEnum.DISABLE_NOTIFICATIONS_CALLBACK;
        } else {
            launchButton = SettingsButtonEnum.SETTINGS_ENABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = SettingsCallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK;
        }

        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                // Кнопка управления проектами
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.PROJECTS_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.PROJECTS_CALLBACK.getCallBackName(),
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
                                        SettingsButtonEnum.SETTINGS_EDIT_TIMEZONE_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.EDIT_TIMEZONE_LINK_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка настроек таймера
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.SETTINGS_EDIT_TIMER_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.EDIT_TIMER_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка настроек языка
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.SETTINGS_EDIT_LANGUAGE_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.EDIT_LANGUAGE_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка удаления данных пользователя
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.SETTINGS_DELETE_USER_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка выхода из меню
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
        );
    }
}

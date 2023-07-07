package com.kuzmichev.AdMetricsBot.telegram.keyboards;

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
public class InlineKeyboards {
    InlineKeyboardMaker inlineKeyboardMaker;
    ScheduledMessageRepository scheduledMessageRepository;

    public InlineKeyboardMarkup inlineKeyboardSettingsMenu(String chatId) {

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
                                        CallBackEnum.PROJECTS_CALLBACK,
                                        null
                                ),
                                // Кнопка включения/выключения рассылки
                                inlineKeyboardMaker.addButton(
                                        launchButton,
                                        launchCallback,
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка настроек часового пояса
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EDIT_TIMEZONE_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_TIMEZONE_CALLBACK,
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
                                // Кнопка настроек языка
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EDIT_LANGUAGE_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_LANGUAGE_CALLBACK,
                                        null
                                ),
                                // Кнопка удаления данных пользователя
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_DELETE_USER_BUTTON.getButtonName(),
                                        CallBackEnum.DELETE_USER_STEP_1_CALLBACK,
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка выхода из меню
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK,
                                        null
                                )
                        )
                )
        );
    }
    public InlineKeyboardMarkup projectsMenu() {

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления проекта
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK,
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка редактирования проекта
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECTS_EDIT_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_EDIT_CALLBACK,
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка назад
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_BACK_CALLBACK,
                                        null
                                ),
                                // Кнопка выход
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK,
                                        null
                                )
                        )
                )
        );
    }

    public InlineKeyboardMarkup projectsNameMenu() {

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка назад
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECTS_CALLBACK,
                                        null
                                ),
                                // Кнопка выход
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK,
                                        null
                                )
                        )
                )
        );
    }


}
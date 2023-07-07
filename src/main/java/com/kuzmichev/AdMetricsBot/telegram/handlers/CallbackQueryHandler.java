package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {
    YandexRepository yandexRepository;
    AddTokensMenu addTokensMenu;
    Registration registration;
    TimeZoneDefinition timeZoneDefinition;
    CheckYaData checkYaData;
    AddYandex addYandex;
    NotificationController notificationController;
    UserStateEditor userStateEditor;
    DeleteUserData deleteUserData;
    ProjectManager projectManager;
    InlineKeyboards inlineKeyboards;
    MessageEditor messageEditor;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String userName = buttonQuery.getMessage().getFrom().getUserName();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        switch (CallBackEnum.valueOf(data)) {

            // Регистрация
            case START_REGISTRATION_CALLBACK -> {
                registration.registerUser(chatId, userName);
                return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
            }
            case PROJECT_CREATE_CALLBACK -> {
                return projectManager.projectCreateStarter(chatId);
            }

            // Меню
            case ENABLE_NOTIFICATIONS_CALLBACK -> {
                notificationController.enableNotifications(chatId);
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE,
                        inlineKeyboards.inlineKeyboardSettingsMenu(chatId));
            }
            case DISABLE_NOTIFICATIONS_CALLBACK -> {
                notificationController.disableNotifications(chatId);
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE,
                        inlineKeyboards.inlineKeyboardSettingsMenu(chatId));
            }
            case DELETE_USER_STEP_1_CALLBACK -> {
                return deleteUserData.askDeleteUserData(chatId);
            }
            case DELETE_USER_STEP_2_CALLBACK -> {
                deleteUserData.deleteUserData(chatId);
                return new SendMessage(
                        chatId,
                        BotMessageEnum.DELETE_USER_DATA_MESSAGE.getMessage());
            }
            case PROJECTS_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_MENU_MESSAGE,
                        inlineKeyboards.projectsMenu(),
                        UserStateEnum.SETTING_PROJECTS_STATE);
            }

            case EDIT_LANGUAGE_CALLBACK -> {
                return new SendMessage(
                        chatId,
                        BotMessageEnum.IN_DEVELOPING_MESSAGE.getMessage());
            }

            case SETTINGS_BACK_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE,
                        inlineKeyboards.inlineKeyboardSettingsMenu(chatId),
                        UserStateEnum.SETTINGS_EDIT_STATE);
            }
            case SETTINGS_EXIT_CALLBACK -> {
                return messageEditor.deleteMessage(
                        chatId,
                        messageId,
                        UserStateEnum.WORKING_STATE);
            }

            // Универсальные
            case PROJECT_CREATE_ASK_NAME_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_NAME_ASK_MESSAGE,
                        inlineKeyboards.projectsNameMenu(),
                        UserStateEnum.PROJECT_CREATE_NAME_STATE);
            }
            case ADD_ACCOUNTS_CALLBACK -> {
                return addTokensMenu.addTokens(chatId);
            }
            case ADD_YANDEX_CALLBACK -> {
                return checkYaData.findYaData(chatId);
            }
            case TEST_MESSAGE_CALLBACK -> {
                return addYandex.testYaData(yandexRepository, chatId);
            }

            // Непонятно
            case NO_CONTINUE_CALLBACK,
                    EDIT_TIMER_CALLBACK -> {
                userStateEditor.editUserState(chatId, UserStateEnum.SETTINGS_EDIT_TIMER_STATE);
                return new SendMessage(chatId, BotMessageEnum.ASK_TIME_MESSAGE.getMessage());
            }
            case EDIT_TIMEZONE_CALLBACK -> {
                return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
            }
            case NOT_DELETE_USER_CALLBACK -> {
                return deleteUserData.notDeleteUser(chatId);
            }
            default -> {
            }
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

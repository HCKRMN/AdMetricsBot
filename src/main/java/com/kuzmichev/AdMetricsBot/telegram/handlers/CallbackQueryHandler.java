package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.*;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageCleaner;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageEditor;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
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
    TempDataRepository tempDataRepository;
    MessageCleaner messageCleaner;
    TempDataSaver tempDataSaver;

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
                null,
                        inlineKeyboards.settingsMenu(chatId));
            }
            case DISABLE_NOTIFICATIONS_CALLBACK -> {
                notificationController.disableNotifications(chatId);
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE,
                        null,
                        inlineKeyboards.settingsMenu(chatId));
            }
            case DELETE_USER_STEP_1_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.DELETE_USER_DATA_ASK_MESSAGE,
                        null,
                        inlineKeyboards.deleteUserDataMenu());
            }
            case DELETE_USER_STEP_2_CALLBACK -> {
                deleteUserData.deleteUserData(chatId);
                messageCleaner.putMessageToQueue(chatId, messageId);
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.DELETE_USER_DATA_MESSAGE,
                        null,
                        null);
            }
            case NOT_DELETE_USER_CALLBACK -> {
                messageCleaner.putMessageToQueue(chatId, messageId);
                return messageEditor.editMessage(
                                chatId,
                                messageId,
                                BotMessageEnum.NOT_DELETE_USER_DATA_MESSAGE,
                                UserStateEnum.WORKING_STATE,
                                null);
            }
            case PROJECTS_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_MENU_MESSAGE,
                        UserStateEnum.SETTINGS_PROJECTS_STATE,
                        inlineKeyboards.projectsMenu());
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
                        UserStateEnum.SETTINGS_EDIT_STATE,
                        inlineKeyboards.settingsMenu(chatId));
            }
            case SETTINGS_EXIT_CALLBACK -> {
                return messageEditor.deleteMessage(
                        chatId,
                        messageId,
                        UserStateEnum.WORKING_STATE);
            }

            case EDIT_TIMER_CALLBACK -> {
                tempDataSaver.tempMessageId(chatId, messageId);
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.ASK_TIME_MESSAGE,
                        UserStateEnum.SETTINGS_EDIT_TIMER_STATE,
                        inlineKeyboards.backAndExitMenu(chatId));
            }

            // Универсальные
            case PROJECT_CREATE_ASK_NAME_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE,
                        UserStateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE,
                        inlineKeyboards.projectCreateMenu());
            }
            case EDIT_TIMEZONE_CALLBACK -> {
                tempDataSaver.tempMessageId(chatId, messageId);
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.TIME_ZONE_DEFINITION_MESSAGE,
                        UserStateEnum.SETTINGS_EDIT_TIMEZONE_STATE,
                        inlineKeyboards.timeZoneMenu(chatId));
            }
            case EDIT_TIMEZONE_MANUAL_CALLBACK -> {
                return messageEditor.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.EDIT_TIMEZONE_MANUAL_MESSAGE,
                        UserStateEnum.EDIT_TIMEZONE_MANUAL_STATE,
                        inlineKeyboards.backAndExitMenu(chatId));
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
            case NO_CONTINUE_CALLBACK -> {
//                спросить время отправки
                userStateEditor.editUserState(chatId, UserStateEnum.SETTINGS_EDIT_TIMER_STATE);
                return new SendMessage(chatId, BotMessageEnum.ASK_TIME_MESSAGE.getMessage());
            }
            default -> {
            }
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

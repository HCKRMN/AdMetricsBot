package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
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
    UserRepository userRepository;
    AddTokensMenu addTokensMenu;
    Registration registration;
    TimeZoneDefinition timeZoneDefinition;
    CheckYaData checkYaData;
    AddYandex addYandex;
    NotificationController notificationController;
    UserStateEditor userStateEditor;
    DeleteUserData deleteUserData;
    BotMessageUtils botMessageUtils;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String userName = buttonQuery.getMessage().getFrom().getUserName();
        String data = buttonQuery.getData();

        switch (CallBackEnum.valueOf(data)) {
            case START_REGISTRATION_CALLBACK -> {
                registration.registerUser(chatId, userName);
                return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
            }
            case ADD_ACCOUNTS_CALLBACK -> {
                return addTokensMenu.addTokens(chatId);
            }
            case ADD_YANDEX_CALLBACK -> {
                 return checkYaData.findYaData(chatId);
            }
            case NO_CONTINUE_CALLBACK,
                 EDIT_TIMER_CALLBACK -> {
                userStateEditor.editUserState(chatId, UserStateEnum.SETTINGS_EDIT_TIMER_STATE.getStateName());
                return new SendMessage(chatId, BotMessageEnum.ASK_TIME_MESSAGE.getMessage());
            }
            case TEST_MESSAGE_CALLBACK ->{
                return  addYandex.testYaData(yandexRepository, chatId);
            }
            case ENABLE_NOTIFICATIONS_CALLBACK ->{
                notificationController.enableNotifications(chatId);
                return  new SendMessage(chatId, BotMessageEnum.ENABLE_NOTIFICATIONS_MESSAGE.getMessage());
            }
            case DISABLE_NOTIFICATIONS_CALLBACK ->{
                notificationController.disableNotifications(chatId);
                return  new SendMessage(chatId, BotMessageEnum.DISABLE_NOTIFICATIONS_MESSAGE.getMessage());
            }
            case EDIT_TIMEZONE_CALLBACK ->{
                return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
            }
            case EDIT_LANGUAGE_CALLBACK ->{
                return new SendMessage(chatId, BotMessageEnum.IN_DEVELOPING_MESSAGE.getMessage());
            }
            case DELETE_USER_STEP_1_CALLBACK ->{
                return deleteUserData.askDeleteUserData(chatId);
            }
            case DELETE_USER_STEP_2_CALLBACK ->{
                deleteUserData.deleteUserData(chatId);
                return new SendMessage(chatId, BotMessageEnum.DELETE_USER_DATA_MESSAGE.getMessage());
            }
            case NOT_DELETE_USER_CALLBACK ->{
                return deleteUserData.notDeleteUser(chatId);
            }

            default -> {}
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
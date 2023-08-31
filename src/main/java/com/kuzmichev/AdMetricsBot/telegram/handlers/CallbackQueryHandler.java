package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.*;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {
    Registration registration;
    TimeZoneDefinition timeZoneDefinition;
    AddYandex addYandex;
    NotificationController notificationController;
    DeleteUserData deleteUserData;
    ProjectManager projectManager;
    InlineKeyboards inlineKeyboards;
    MessageWithReturn messageWithReturn;
    MessageManagementService messageManagementService;
    TempDataSaver tempDataSaver;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    TempDataRepository tempDataRepository;
    InputsManager inputsManager;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String userName = buttonQuery.getMessage().getFrom().getUserName();
        String data = buttonQuery.getData();
        String projectId = "";
        int messageId = buttonQuery.getMessage().getMessageId();
        int currentPage = 1;
        String inputName = null;


        // Секция обработки динамический колбеков с данными
        String regexProject = "project_.+";
        Map<String, String> projectData = DynamicCallback.handleDynamicCallback(data, regexProject, "project_");
        if (!projectData.isEmpty()) {
            projectId = projectData.get("value");
            tempDataSaver.tempProjectId(chatId, projectId);
            data = "SOME_PROJECT_CALLBACK";
        }

        String regexPage = "page_.+";
        Map<String, String> pageData = DynamicCallback.handleDynamicCallback(data, regexPage, "page_");
        if (!pageData.isEmpty()) {
            currentPage = Integer.parseInt(pageData.get("value"));
            data = "PROJECT_GET_LIST_CALLBACK";
        }

        String regexInput = "input_.+";
        Map<String, String> inputData = DynamicCallback.handleDynamicCallback(data, regexInput, "input_");
        if (!inputData.isEmpty()) {
            inputName = inputData.get("value");
            System.out.println(inputName);
            data = "PROJECT_INPUT_DELETE_CALLBACK";
        }




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
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                null,
                        inlineKeyboards.settingsMenu(chatId));
            }
            case DISABLE_NOTIFICATIONS_CALLBACK -> {
                notificationController.disableNotifications(chatId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.settingsMenu(chatId));
            }
            case DELETE_USER_STEP_1_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.deleteUserDataMenu());
            }
            case DELETE_USER_STEP_2_CALLBACK -> {
                deleteUserData.deleteUserData(chatId);
                messageManagementService.putMessageToQueue(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.DELETE_USER_DATA_MESSAGE.getMessage(),
                        null,
                        null);
            }
            case NOT_DELETE_USER_CALLBACK -> {
                messageManagementService.putMessageToQueue(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.NOT_DELETE_USER_DATA_MESSAGE.getMessage(),
                        UserStateEnum.WORKING_STATE,
                        null);
            }
            case PROJECTS_CALLBACK -> {
                tempDataSaver.tempLastMessageId(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_MENU_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_PROJECTS_STATE,
                        inlineKeyboards.projectsMenu(chatId));
            }
            case EDIT_LANGUAGE_CALLBACK -> {
                return new SendMessage(
                        chatId,
                        BotMessageEnum.IN_DEVELOPING_MESSAGE.getMessage());
            }
            case SETTINGS_BACK_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_EDIT_STATE,
                        inlineKeyboards.settingsMenu(chatId));
            }
            case SETTINGS_EXIT_CALLBACK, NOT_DELETE_PROJECT_CALLBACK -> {
                return messageWithReturn.deleteMessage(
                        chatId,
                        messageId,
                        UserStateEnum.WORKING_STATE);
            }
            case EDIT_TIMER_CALLBACK -> {
                tempDataSaver.tempLastMessageId(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.ASK_TIME_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_EDIT_TIMER_STATE,
                        inlineKeyboards.backAndExitMenu(chatId));
            }
            case PROJECT_GET_LIST_CALLBACK -> {
//                messageManagementService.putMessageToQueue(chatId, messageId);
                Optional<User> userOptional = userRepository.findByChatId(chatId);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    user.setProjectsPage(currentPage);
                    userRepository.save(user);
                }
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_PROJECTS_LIST_MENU_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.projectListMenu(chatId));
            }
            case SOME_PROJECT_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        projectRepository.findProjectNameByProjectId(projectId),
                        null,
                        inlineKeyboards.someProjectMenu(chatId));
            }
            case PROJECT_DELETE_STEP_1_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_DELETE_STEP_1_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.deleteProjectMenu());
            }

            case PROJECT_DELETE_STEP_2_CALLBACK -> {
                projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
                projectRepository.removeProjectByProjectId(projectId);
                userRepository.decrementProjectsCount(chatId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_DELETE_STEP_2_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.done()
                );
            }
            case PROJECT_INPUT_DELETE_CALLBACK -> {
                if (inputName != null){
                    projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
                    inputsManager.deleteInputs(projectId, inputName);
                }
                 return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_INPUT_DELETE_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.deleteInputsMenu(chatId));
            }
            case ADD_BITRIX_STEP_1_CALLBACK -> {
                tempDataSaver.tempLastMessageId(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.ADD_BITRIX_STEP_1_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_PROJECT_ADD_BITRIX_STATE,
                        inlineKeyboards.backAndExitMenu(chatId));
            }





            // Универсальные
            case PROJECT_CREATE_ASK_NAME_CALLBACK -> {
                tempDataSaver.tempLastMessageId(chatId, messageId);
                System.out.println("messageId: " + messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE,
                        inlineKeyboards.projectCreateMenu());
            }
            case EDIT_TIMEZONE_CALLBACK -> {
                tempDataSaver.tempLastMessageId(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_EDIT_TIMEZONE_STATE,
                        inlineKeyboards.timeZoneMenu(chatId));
            }
            case EDIT_TIMEZONE_MANUAL_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.EDIT_TIMEZONE_MANUAL_MESSAGE.getMessage(),
                        UserStateEnum.EDIT_TIMEZONE_MANUAL_STATE,
                        inlineKeyboards.backAndExitMenu(chatId));
            }
            case ADD_TOKENS_CALLBACK, PROJECT_ADD_TOKEN_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.ADD_TOKENS_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_PROJECT_ADD_TOKENS_STATE,
                        inlineKeyboards.addTokensMenu(chatId));
            }
            case ADD_YANDEX_CALLBACK -> {
                tempDataSaver.tempLastMessageId(chatId, messageId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.ADD_YANDEX_MESSAGE.getMessage(),
                        UserStateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE,
                        inlineKeyboards.addYandexMenu(chatId));
            }
            case TEST_YANDEX_CALLBACK -> {
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        addYandex.testYandex(chatId),
                        UserStateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE,
                        inlineKeyboards.addYandexTestMenu());

            }

            default -> {
            }
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

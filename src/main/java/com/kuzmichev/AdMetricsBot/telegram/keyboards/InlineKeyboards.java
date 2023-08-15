package com.kuzmichev.AdMetricsBot.telegram.keyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddYandex;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class InlineKeyboards {
    final InlineKeyboardMaker inlineKeyboardMaker;
    final ScheduledMessageRepository scheduledMessageRepository;
    final UserRepository userRepository;
    final AddYandex addYandex;
    final ProjectRepository projectRepository;

    @Value("${telegram.webhook-path}")
    String link;

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
                                        CallBackEnum.EDIT_TIMEZONE_CALLBACK.getCallBackName(),
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
    public InlineKeyboardMarkup projectsMenu() {

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления проекта
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка получения списка проектов
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.PROJECTS_GET_LIST_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка назад
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_BACK_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка выход
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }
// Это кнопки меню второго уровня, оно универсальное. Подходит для меню проектов, настройки времени
    public InlineKeyboardMarkup projectCreateMenu() {

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка назад
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECTS_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка выход
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }


    public InlineKeyboardMarkup deleteUserDataMenu() {

        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.DELETE_USER_DATA_BUTTON.getButtonName(),
                                                CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName(),
                                                null
                                        ),
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.NOT_DELETE_USER_DATA_BUTTON.getButtonName(),
                                                CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName(),
                                                null
                                        )
                                )
                        )
                );

    }

    // Это кнопки меню второго уровня, оно универсальное. Подходит для настройки времени, настройки проектов и тд
    public List<InlineKeyboardButton> backAndExitMenuButtons(String chatId) {

        String userState = userRepository.getUserStateByChatId(chatId);
        System.out.println(userState);

        CallBackEnum backButtonCallBackEnum;

        switch (UserStateEnum.valueOf(userState)){
            case    SETTINGS_EDIT_STATE,
                    SETTINGS_EDIT_TIMER_STATE,
                    SETTINGS_PROJECTS_STATE,
                    SETTINGS_EDIT_TIMEZONE_STATE
                    -> backButtonCallBackEnum = CallBackEnum.SETTINGS_BACK_CALLBACK;

            case SETTINGS_PROJECT_CREATE_STATE,
                    SETTINGS_PROJECT_ADD_TOKENS_STATE
                    -> backButtonCallBackEnum = CallBackEnum.PROJECTS_CALLBACK;

            default -> backButtonCallBackEnum = CallBackEnum.SETTINGS_EXIT_CALLBACK;

        }

        return inlineKeyboardMaker.addRow(
                            // Кнопка назад
                            inlineKeyboardMaker.addButton(
                                    ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                                    backButtonCallBackEnum.getCallBackName(),
                                    null
                            ),
                            // Кнопка выход
                            inlineKeyboardMaker.addButton(
                                    ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                    CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                    null
                            )

            );
    }
    // Это универсальное меню с двумя кнопками: Назад и Выход
    public InlineKeyboardMarkup backAndExitMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        backAndExitMenuButtons(chatId)
                )
        );
    }

    // Кнопка отмены, не помню зачем
//    public InlineKeyboardMarkup cancel() {
//        return inlineKeyboardMaker.addMarkup(
//                inlineKeyboardMaker.addRows(
//                        inlineKeyboardMaker.addRow(
//                                inlineKeyboardMaker.addButton(
//                                        ButtonNameEnum.CANCEL_BUTTON.getButtonName(),
//                                        CallBackEnum.SETTINGS_EXIT_CALLBACK,
//                                        null
//                                )
//                        )
//                )
//        );
//    }
    // Кнопка Готово
    public InlineKeyboardMarkup done() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.DONE_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }

    public InlineKeyboardMarkup timeZoneMenu(String chatId) {

        String ipToTimeZoneLink = link + "/getip" +
                "?chatId=" + chatId;

        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        // Добавить временную зону по ссылке
                                        ButtonNameEnum.LINK_BUTTON.getButtonName(),
                                        null,
                                        ipToTimeZoneLink
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        // Ручной ввод текущего времени пользователя
                                        ButtonNameEnum.MANUAL_INPUT_BUTTON.getButtonName(),
                                        CallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        backAndExitMenuButtons(chatId)
                )
        );
    }

    public InlineKeyboardMarkup addTokensMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Яндекс
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_YANDEX_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления VK
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_VK_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_VK_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления MyTarget
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_MYTARGET_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_MYTARGET_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Bitrix
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_BITRIX_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_BITRIX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления AmoCRM
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_AMOCRM_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_AMOCRM_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Yclients
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_YCLIENTS_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        backAndExitMenuButtons(chatId)
                )
        );
    }
    public InlineKeyboardMarkup addYandexMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.YANDEX_ADD_TOKEN_LINK_BUTTON.getButtonName(),
                                        null,
                                        addYandex.getYaAuthorizationUrl(chatId)
                                )
//                                        на всякий случай оставлю кнопку получения доступа к апи, вроде как ненужно, но пусть будет
//                                        ,
//                                        inlineKeyboardMaker.addButton(
//                                                ButtonNameEnum.YANDEX_API_SETTINGS_BUTTON.getButtonName(),
//                                                null,
//                                                addYandex.getApiSettingsUrl(chatId)
//                                        )
                        ),
                            //Старая кнопка тестового запроса, пока что убрана чтобы пользователь не мог нажать без регистрации
//                        inlineKeyboardMaker.addRow(
//                                inlineKeyboardMaker.addButton(
//                                        ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
//                                        CallBackEnum.TEST_MESSAGE_CALLBACK,
//                                        null
//                                )
//                        ),
                        backAndExitMenuButtons(chatId)
                )
        );
    }

    public InlineKeyboardMarkup addYandexTestMenu() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        //Кнопка тестового запроса
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
                                        CallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка добавить другие токены
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_TOKENS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка готово
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.DONE_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }

    public InlineKeyboardMarkup someProjectMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                    inlineKeyboardMaker.addRows(
                            inlineKeyboardMaker.addRow(
                                    inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.PROJECT_ADD_TOKEN_BUTTON.getButtonName(),
                                            CallBackEnum.PROJECT_ADD_TOKEN_CALLBACK.getCallBackName(),
                                            null
                                    )
                            ),
                            inlineKeyboardMaker.addRow(
                                    inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.PROJECT_DELETE_TOKEN_BUTTON.getButtonName(),
                                            CallBackEnum.PROJECT_DELETE_TOKEN_CALLBACK.getCallBackName(),
                                            null
                                    )
                            ),
                            inlineKeyboardMaker.addRow(
                                    inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.PROJECT_GET_INFO_BUTTON.getButtonName(),
                                            CallBackEnum.PROJECT_GET_INFO_CALLBACK.getCallBackName(),
                                            null
                                    )
                            ),
                            inlineKeyboardMaker.addRow(
                                    inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.PROJECT_DELETE_BUTTON.getButtonName(),
                                            CallBackEnum.PROJECT_DELETE_CALLBACK.getCallBackName(),
                                            null

                                    )
                            ),
                            backAndExitMenuButtons(chatId)
                    )
        );
    }


    public InlineKeyboardMarkup projectListMenu(String chatId) {
        String projectName;
        String projectCallback;

        int projectsPerPage = 5;
        int currentPage = userRepository.getProjectsPageByChatId(chatId);

        List<Project> projects = projectRepository.findProjectsByChatId(chatId);

        int startIndex = currentPage * projectsPerPage - projectsPerPage;
        int endIndex = Math.min(startIndex + projectsPerPage, projects.size());

        List<Project> projectsForPage = projects.subList(startIndex, endIndex);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Project project : projectsForPage) {
            projectName = project.getProjectName();
            projectCallback = "project_" + project.getProjectId();
            rows.add(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    projectName,
                                    projectCallback,
                                    null
                            )
                    )
            );
        }
        rows.add(paginationButtons(currentPage, projects.size() / projectsPerPage));
        rows.add(backAndExitMenuButtons(chatId));

        return inlineKeyboardMaker.addMarkup(rows);
    }

    private List<InlineKeyboardButton> paginationButtons(int currentPage, int totalPages) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        if (currentPage > 1) {
            buttons.add(
                    inlineKeyboardMaker.addButton(
                            ButtonNameEnum.PROJECT_PREVIOUS_PAGE_BUTTON.getButtonName(),
                            CallBackEnum.PROJECT_PAGE_CALLBACK.getCallBackName() + (currentPage - 1),
                            null
                    )
            );
        }

        if (currentPage < totalPages ) {
            buttons.add(
                    inlineKeyboardMaker.addButton(
                            ButtonNameEnum.PROJECT_NEXT_PAGE_BUTTON.getButtonName(),
                            CallBackEnum.PROJECT_PAGE_CALLBACK.getCallBackName() + (currentPage + 1),
                            null
                    )
            );
        }

        return buttons;
    }





}
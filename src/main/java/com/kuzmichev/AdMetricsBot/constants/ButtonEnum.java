package com.kuzmichev.AdMetricsBot.constants;

public enum ButtonEnum {
    REGISTRATION_BUTTON("Регистрация"),


    LINK_BUTTON("ССЫЛКА"),
    PROJECT_CREATE_BUTTON("Создать проект"),
    UNIVERSAL_CONTINUE_BUTTON("Продолжить"),
    TEST_INPUTS_BUTTON("Тестовый запрос"),


    YANDEX_ADD_TOKEN_LINK_BUTTON("Добавить токен Яндекс"),
    DELETE_USER_DATA_BUTTON("Да, удалить"),
    NOT_DELETE_USER_DATA_BUTTON("Нет, продолжить"),
    YANDEX_API_SETTINGS_BUTTON("Настройки API"),
    ADD_YANDEX_BUTTON("Яндекс Директ"),
    ADD_VK_BUTTON("VK"),
    ADD_MYTARGET_BUTTON("MyTarget"),
    ADD_AMOCRM_BUTTON("AmoCRM"),
    ADD_YCLIENTS_BUTTON("Yclients"),
    ADD_BITRIX_BUTTON("Bitrix24"),
    SETTINGS_ENABLE_NOTIFICATIONS_BUTTON("Вкл. уведомления"),
    SETTINGS_DISABLE_NOTIFICATIONS_BUTTON("Откл. уведомления"),
    SETTINGS_EDIT_TIMEZONE_BUTTON("Часовой пояс"),
    SETTINGS_EDIT_LANGUAGE_BUTTON("Язык"),
    SETTINGS_EDIT_TIMER_BUTTON("Время отправки"),
    SETTINGS_ADD_ACCOUNTS_BUTTON("Добавить другие аккаунты"),
    SETTINGS_DELETE_USER_BUTTON("Удалить данные"),
    SETTINGS_EXIT_BUTTON("Выйти из меню"),
    SETTINGS_BACK_BUTTON("Назад"),
    PROJECTS_BUTTON("Проекты"),
    PROJECTS_GET_LIST_BUTTON("Мои проекты"),
    PROJECT_ADD_TOKEN_BUTTON("Добавить источник"),
    PROJECT_DELETE_TOKEN_BUTTON("Удалить источник"),
    PROJECT_GET_INFO_BUTTON("Получить данные"),
    PROJECT_DELETE_BUTTON("Удалить проект"),
    PROJECT_PREVIOUS_PAGE_BUTTON("Предыдущие"),
    PROJECT_NEXT_PAGE_BUTTON("Следующие"),
    DELETE_PROJECT_BUTTON("Удалить"),
    CANCEL_BUTTON("Отмена"),
    DONE_BUTTON("Готово"),
    MANUAL_INPUT_BUTTON("Ручной ввод"),
    CLOSE_BUTTON("Закрыть")
    ;


    private final String buttonName;

    ButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
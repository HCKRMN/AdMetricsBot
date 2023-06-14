package com.kuzmichev.AdMetricsBot.constants;

public enum ButtonNameEnum {
    REGISTRATION_BUTTON("Регистрация"),
    LINK_BUTTON("ССЫЛКА"),
    YANDEX_ADD_TOKEN_LINK_BUTTON("Добавить токен Яндекс"),
    YANDEX_API_SETTINGS_BUTTON("Настройки API"),
    ADD_YANDEX_BUTTON("Яндекс Директ"),
    YES_ADD_BUTTON("Да, добавить"),
    CONTINUE_BUTTON("Продолжить"),
    NO_CONTINUE_BUTTON("Нет, продолжить"),
    TEST_YANDEX_BUTTON("Тест запроса Яндекс"),
    SETTINGS_ENABLE_NOTIFICATIONS_BUTTON("Включить уведомления"),
    SETTINGS_DISABLE_NOTIFICATIONS_BUTTON("Выключить уведомления"),
    SETTINGS_EDIT_TIMEZONE_BUTTON("Часовой пояс"),
    SETTINGS_EDIT_LANGUAGE_BUTTON("Язык"),
    SETTINGS_EDIT_TIMER_BUTTON("Время отправки"),
    SETTINGS_ADD_ACCOUNTS_BUTTON("Добавить аккаунты"),
    SETTINGS_DELETE_USER_BUTTON("Удалить данные"),
    BACK_BUTTON("Назад");


    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
package com.kuzmichev.AdMetricsBot.constants;

public enum ButtonNameEnum {
    REGISTRATION_BUTTON("Регистрация"),
    LINK_BUTTON("ССЫЛКА"),
    ADD_YANDEX_BUTTON("Яндекс Директ"),
    YES_ADD_BUTTON("Да, добавить"),
    CONTINUE_BUTTON("Продолжить"),
    NO_CONTINUE_BUTTON("Нет, продолжить"),
    TEST_YANDEX_BUTTON("Тест запроса Яндекс");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
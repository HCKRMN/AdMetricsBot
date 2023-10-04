package com.kuzmichev.AdMetricsBot.constants.universalEnums;

public enum UniversalButtonEnum {
    LINK_BUTTON("ССЫЛКА"),
    PROJECT_CREATE_BUTTON("Создать проект"),
    UNIVERSAL_CONTINUE_BUTTON("Продолжить"),
    TEST_INPUTS_BUTTON("Тестовый запрос"),
    ;


    private final String buttonName;

    UniversalButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
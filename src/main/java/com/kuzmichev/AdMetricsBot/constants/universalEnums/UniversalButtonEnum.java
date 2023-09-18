package com.kuzmichev.AdMetricsBot.constants.universalEnums;

public enum UniversalButtonEnum {
    LINK_BUTTON("ССЫЛКА"),
    PROJECT_CREATE_BUTTON("Создать проект"),
    ;


    private final String buttonName;

    UniversalButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}

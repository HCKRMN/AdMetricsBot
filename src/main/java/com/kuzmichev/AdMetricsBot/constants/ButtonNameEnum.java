package com.kuzmichev.AdMetricsBot.constants;

public enum ButtonNameEnum {
    REGISTRATION_BUTTON("Регистрация");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
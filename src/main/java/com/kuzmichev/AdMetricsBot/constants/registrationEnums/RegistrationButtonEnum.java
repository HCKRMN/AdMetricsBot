package com.kuzmichev.AdMetricsBot.constants.registrationEnums;

public enum RegistrationButtonEnum {
    REGISTRATION_BUTTON("Регистрация"),
    ;

    private final String buttonName;

    RegistrationButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}

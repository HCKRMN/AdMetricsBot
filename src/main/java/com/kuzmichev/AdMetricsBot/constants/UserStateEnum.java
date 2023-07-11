package com.kuzmichev.AdMetricsBot.constants;

public enum UserStateEnum {

    // Регистрация
    REGISTRATION_STATE("REGISTRATION_STATE"),

    // Настройки
    SETTINGS_EDIT_STATE("SETTINGS_EDIT_STATE"),
        SETTINGS_PROJECTS_STATE("SETTINGS_PROJECTS_STATE"),
            SETTINGS_PROJECT_CREATE_STATE("SETTINGS_PROJECT_CREATE_STATE"),
                SETTINGS_PROJECT_CREATE_ASK_NAME_STATE("SETTINGS_PROJECT_CREATE_ASK_NAME_STATE"),
            SETTINGS_PROJECT_EDIT_STATE("SETTINGS_PROJECT_EDIT_STATE"),
        SETTINGS_EDIT_TIMER_STATE("SETTINGS_EDIT_TIMER_STATE"),
        SETTINGS_EDIT_TIMEZONE_STATE("SETTINGS_EDIT_TIMEZONE_STATE"),

    // Универсальные
    WORKING_STATE("WORKING_STATE"),
    EDIT_TIMEZONE_MANUAL_STATE("EDIT_TIMEZONE_MANUAL_STATE"),
    ;

    private final String stateName;

    UserStateEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

}

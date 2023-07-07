package com.kuzmichev.AdMetricsBot.constants;

public enum UserStateEnum {
    REGISTRATION_STATE("REGISTRATION_STATE"),
    SETTINGS_EDIT_STATE("SETTINGS_EDIT_STATE"),
    SETTINGS_EDIT_TIMER_STATE("SETTINGS_EDIT_TIMER_STATE"),
    SETTING_PROJECTS_STATE("SETTING_PROJECTS_STATE"),
    WORKING_STATE("WORKING_STATE"),
    PROJECT_CREATE_NAME_STATE("PROJECT_CREATE_NAME_STATE"),
    ;

    private final String stateName;

    UserStateEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

}

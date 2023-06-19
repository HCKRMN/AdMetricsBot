package com.kuzmichev.AdMetricsBot.constants;

public enum UserStatesEnum {
    REGISTRATION_STATE("REGISTRATION_STATE"),
    SETTINGS_EDIT_STATE("SETTINGS_EDIT_STATE"),
    SETTINGS_EDIT_TIMER_STATE("SETTINGS_EDIT_TIMER_STATE"),
    WORKING_STATE("WORKING_STATE"),
    ;

    private final String stateName;

    UserStatesEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

}

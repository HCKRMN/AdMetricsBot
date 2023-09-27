package com.kuzmichev.AdMetricsBot.constants.registrationEnums;

public enum RegistrationStateEnum {
    REGISTRATION_STATE("REGISTRATION_STATE"),
    REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE("REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE"),
    REGISTRATION_ADD_INPUTS_STATE("REGISTRATION_ADD_INPUTS_STATE"),
    REGISTRATION_EDIT_TIMEZONE_MANUAL_STATE("REGISTRATION_EDIT_TIMEZONE_MANUAL_STATE"),

            ;

    private final String stateName;

    RegistrationStateEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}

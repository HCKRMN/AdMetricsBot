package com.kuzmichev.AdMetricsBot.constants;

public enum UserStatesEnum {
    REGISTRATION_STEP_1_STATE("REGISTRATION_STEP_1_STATE"),
    REGISTRATION_STEP_2_STATE("REGISTRATION_STEP_2_STATE"),

    START_NOTIFICATIONS_STATE("START_NOTIFICATIONS_STATE"),
    STOP_NOTIFICATIONS_STATE("STOP_NOTIFICATIONS_STATE");



    private final String stateName;

    UserStatesEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

}

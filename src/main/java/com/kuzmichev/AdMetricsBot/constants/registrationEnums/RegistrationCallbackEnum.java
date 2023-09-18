package com.kuzmichev.AdMetricsBot.constants.registrationEnums;

public enum RegistrationCallbackEnum {
    START_REGISTRATION_CALLBACK("START_REGISTRATION_CALLBACK"),

    ;

    private final String callBackName;

    RegistrationCallbackEnum(String callBackName) {
        this.callBackName = callBackName;
    }

    public String getCallBackName() {
        return callBackName;
    }
}

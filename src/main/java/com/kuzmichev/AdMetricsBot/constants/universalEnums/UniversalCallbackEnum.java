package com.kuzmichev.AdMetricsBot.constants.universalEnums;

public enum UniversalCallbackEnum {
    PROJECT_CREATE_ASK_NAME_CALLBACK("PROJECT_CREATE_ASK_NAME_CALLBACK"),
    UNIVERSAL_EDIT_TIMER_CALLBACK("UNIVERSAL_EDIT_TIMER_CALLBACK"),
    ;

    private final String callBackName;

    UniversalCallbackEnum(String callBackName) {
        this.callBackName = callBackName;
    }

    public String getCallBackName() {
        return callBackName;
    }
}

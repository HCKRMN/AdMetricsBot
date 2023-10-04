package com.kuzmichev.AdMetricsBot.constants.settingsEnums;

public enum SettingsCallBackEnum {
    ADD_YANDEX_CALLBACK("ADD_YANDEX_CALLBACK"),
    ADD_VK_CALLBACK("ADD_VK_CALLBACK"),
    ADD_MYTARGET_CALLBACK("ADD_MYTARGET_CALLBACK"),
    ADD_BITRIX_STEP_1_CALLBACK("ADD_BITRIX_STEP_1_CALLBACK"),
    ADD_AMOCRM_CALLBACK("ADD_AMOCRM_CALLBACK"),
    ADD_YCLIENTS_CALLBACK("ADD_YCLIENTS_CALLBACK"),
    EDIT_TIMEZONE_LINK_CALLBACK("EDIT_TIMEZONE_CALLBACK"),
    EDIT_TIMEZONE_MANUAL_CALLBACK("EDIT_TIMEZONE_MANUAL_CALLBACK"),
    EDIT_LANGUAGE_CALLBACK("EDIT_LANGUAGE_CALLBACK"),
    ADD_INPUTS_CALLBACK("ADD_INPUTS_CALLBACK"),
    DELETE_USER_STEP_1_CALLBACK("DELETE_USER_STEP_1_CALLBACK"),
    DELETE_USER_STEP_2_CALLBACK("DELETE_USER_STEP_2_CALLBACK"),
    YES_ADD_CALLBACK("YES_ADD_CALLBACK"),
    ENABLE_NOTIFICATIONS_CALLBACK("ENABLE_NOTIFICATIONS_CALLBACK"),
    DISABLE_NOTIFICATIONS_CALLBACK("DISABLE_NOTIFICATIONS_CALLBACK"),
    NOT_DELETE_USER_CALLBACK("NOT_DELETE_USER_CALLBACK"),
    SETTINGS_EXIT_CALLBACK("SETTINGS_EXIT_CALLBACK"),
    SETTINGS_BACK_CALLBACK("SETTINGS_BACK_CALLBACK"),
    PROJECT_CREATE_CALLBACK("PROJECT_CREATE_CALLBACK"),
    PROJECT_EDIT_CALLBACK("PROJECT_EDIT_CALLBACK"),
    PROJECT_INPUT_DELETE_CALLBACK("PROJECT_INPUT_DELETE_CALLBACK"),
    PROJECT_INPUT_DELETE_STEP_2_CALLBACK("PROJECT_INPUT_DELETE_STEP_2_CALLBACK"),
    PROJECT_DELETE_STEP_1_CALLBACK("PROJECT_DELETE_STEP_1_CALLBACK"),
    PROJECT_GET_INFO_CALLBACK("PROJECT_GET_INFO_CALLBACK"),
    PROJECT_GET_LIST_CALLBACK("PROJECT_GET_LIST_CALLBACK"),
    PROJECTS_CALLBACK("PROJECTS_CALLBACK"),
    TEST_YANDEX_CALLBACK("TEST_YANDEX_CALLBACK"),
    SOME_PROJECT_CALLBACK("SOME_PROJECT_CALLBACK"),
    PROJECT_PAGE_CALLBACK("page_"),
    INPUT_CALLBACK("input_"),
    PROJECT_DELETE_STEP_2_CALLBACK("PROJECT_DELETE_STEP_2_CALLBACK"),
    NOT_DELETE_PROJECT_CALLBACK("NOT_DELETE_PROJECT_CALLBACK"),
    TEST_BITRIX_CALLBACK("TEST_BITRIX_CALLBACK"),


    ;

    private final String callBackName;

    SettingsCallBackEnum(String callBackName) {
        this.callBackName = callBackName;
    }

    public String getCallBackName() {
        return callBackName;
    }
}
package com.kuzmichev.AdMetricsBot.constants.universalEnums;

public enum UniversalMessageEnum {
    PROJECT_CREATE_ASK_NAME_MESSAGE("Введите название проекта, не более 30 символов"),
    PROJECT_NAME_INVALID_MESSAGE("Вы ввели некорректное название проекта. Введите название из 30 символов, " +
            "используйте кириллицу или латиницу, а также цифры."),
    ;

    private final String message;

    UniversalMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

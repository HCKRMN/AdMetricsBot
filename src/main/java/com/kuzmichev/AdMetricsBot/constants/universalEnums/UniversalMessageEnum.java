package com.kuzmichev.AdMetricsBot.constants.universalEnums;

public enum UniversalMessageEnum {
    PROJECT_CREATE_ASK_NAME_MESSAGE("Введите название проекта, не более 30 символов"),
    PROJECT_NAME_INVALID_MESSAGE("Вы ввели некорректное название проекта. Введите название из 30 символов, " +
            "используйте кириллицу или латиницу, а также цифры."),
    TIME_ZONE_DEFINITION_MESSAGE("Перейдите по ссылке чтобы добавить разницу часовых поясов. " +
            "Обязательно выключите VPN, если используете его. \n" +
            "Вы также можете добавить разницу часовых поясов вручную."),
    TIMER_ADD_ERROR_MESSAGE("Вы не установили часовой пояс! \n" +
            "Нажмите на кнопку чтобы добавить разницу часовых поясов. \n"),
    BITRIX_LEADS_COUNT_MESSAGE("Количество входящих лидов в битриксе: "),
    BITRIX_ERROR_MESSAGE("Произошла ошибка при получении данных от битрикса. ");



    ;

    private final String message;

    UniversalMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

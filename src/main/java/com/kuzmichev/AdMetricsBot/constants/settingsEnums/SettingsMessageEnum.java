package com.kuzmichev.AdMetricsBot.constants.settingsEnums;

/**
 * Текстовые сообщения, посылаемые ботом
 */
public enum SettingsMessageEnum {

    TIME_ZONE_DEFINITION_MESSAGE("Перейдите по ссылке чтобы добавить разницу часовых поясов. " +
            "Обязательно выключите VPN, если используете его.\n" +
            "Вы также можете добавить разницу часовых поясов вручную."),
    TIME_ZONE_DEFINITION_COMPLETE_MESSAGE("Разница часовых поясов успешно добавлена"),
    EDIT_TIMEZONE_MANUAL_MESSAGE("Укажите ваше текущее время в формате «23 59»"),
    ADD_YANDEX_MESSAGE("Перейдите по ссылке чтобы подключить аккаунт Яндекс Директ"),
    ADD_YANDEX_TEST_MESSAGE("Проверьте получение данных с аккаунта Яндекс Директ"),
    ADD_BITRIX_STEP_1_MESSAGE("Введите домен вашего портала Битрикс24 в формате, без кавычек: \n«test.bitrix24.ru». " +
            "\nЕсли у вас собственный домен без bitrix.ru, введите его."),
    ADD_BITRIX_STEP_2_MESSAGE("Теперь перейдите по ссылке"),
    ADD_TOKENS_MESSAGE("Добавьте источники данных"),
    ASK_TIME_MESSAGE("Укажите время отправки уведомления в формате «23 59»"),
    DELETE_USER_DATA_MESSAGE("Ваши данные полностью удалены"),
    DELETE_USER_DATA_ASK_MESSAGE("Вы уверены, что хотите удалить данные?"),
    YANDEX_ERROR_GET_RESULT_MESSAGE("Ошибка при получении данных Яндекс"),
    YANDEX_RESULT_MESSAGE("Затраты на рекламу в Яндекс директ: "),
    YANDEX_ERROR_GET_TOKEN_MESSAGE("Вы не зарегистрированы в Яндекс Директ"),
    SETTINGS_MENU_MESSAGE("В этом меню вы можете изменить время отправки уведомлений, часовой пояс, " +
            "подключить новые аккаунты, отключить уведомления, а также удалить полностью свои данные."),
    TIMER_ADDED_MESSAGE("Таймер установлен на "),
    IN_DEVELOPING_MESSAGE("Эта опция находится в разработке"),
    PROJECT_CREATE_START_MESSAGE("Вы можете создать несколько проектов и подключить в них разные аккаунты рекламных источников и СРМ"),
    PROJECT_CREATE_DONE_MESSAGE("Проект успешно создан"),
    PROJECT_MENU_MESSAGE("Создайте новый, отредактируйте существующий или удалите ненужный проект"),
    SETTINGS_PROJECTS_LIST_MENU_MESSAGE("В этом меню отображены все ваши проекты. Вы можете изменить подключенные источники данных или удалить проект"),
    PROJECT_DELETE_STEP_1_MESSAGE("Вы уверены, что хотите удалить проект?"),
    PROJECT_DELETE_STEP_2_MESSAGE("Проект удалён"),
    NOT_DELETE_PROJECT_MESSAGE("Удаление проекта отменено"),
    PROJECT_INPUT_DELETE_MESSAGE("Выберите источник данных, который хотите удалить"),


    INVALID_TIME_MESSAGE("Неверный формат времени. \nВведите время в формате: \n«23 59», \n«00 00». \n\nБез кавычек!"),
    INVALID_BITRIXDOMAIN_MESSAGE("Неверный формат домена. \nВведите значение в формате, без кавычек: \n«test.bitrix24.ru»" +
            "\n Если у вас собственный домен без bitrix24.ru, введите его."),
    NON_COMMAND_MESSAGE("Вы ввели неизвестную команду"),
    DISABLE_NOTIFICATIONS_MESSAGE("Уведомления отключены"),
    ENABLE_NOTIFICATIONS_MESSAGE("Уведомления включены"),
    NOT_DELETE_USER_DATA_MESSAGE("Мы рады что вы продолжаете пользоваться нашим ботом!"),


    //ошибки при обработке callback-ов
    EXCEPTION_BAD_BUTTON_NAME_MESSAGE("Неверное значение кнопки. Крайне странно. Попробуйте позже"),

    //прочие ошибки
    EXCEPTION_ILLEGAL_MESSAGE("Неверный формат сообщения"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так"),

    HELP_MESSAGE("Список команд: \n" +  "Потом написать список команд");
    private final String message;

    SettingsMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
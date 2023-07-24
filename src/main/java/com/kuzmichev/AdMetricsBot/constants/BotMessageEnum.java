package com.kuzmichev.AdMetricsBot.constants;

/**
 * Текстовые сообщения, посылаемые ботом
 */
public enum BotMessageEnum {
    START_MESSAGE("\uD83D\uDC4B Привет! Этот бот собирает данные по рекламным источникам и срм, " +
            "после чего в заданное время регулярно отправляет сообщение с краткой статистикой. \n\n" +
            "Благодаря этому ты можешь держать руку на пульсе своего бизнеса и принимать своевременные решения!" +
            "Зарегистрируйтесь, чтобы начать работу\uD83D\uDC47"),
    TIME_ZONE_DEFINITION_MESSAGE("Перейдите по ссылке чтобы добавить разницу часовых поясов. " +
            "Обязательно выключите VPN, если используете его.\n" +
            "Вы также можете добавить разницу часовых поясов вручную."),
    TIME_ZONE_DEFINITION_COMPLETE_MESSAGE("Разница часовых поясов успешно добавлена"),
    EDIT_TIMEZONE_MANUAL_MESSAGE("Укажите ваше текущщее время в формате «23 59»"),
    ADD_YANDEX_MESSAGE("Перейдите по ссылке чтобы подключить аккаунт Яндекс Директ"),
    ADD_YANDEX_TEST_MESSAGE("Проверьте получение данных с аккаунта Яндекс Директ"),
    ADD_TOKENS_MESSAGE("Добавьте источники данных"),
    ACCOUNT_ALREADY_ADDED_MESSAGE("Аккаунт Яндекс уже добавлен, хотите подключить другие сервисы?"),
    ASK_TIME_MESSAGE("Укажите время отправки уведомления в формате «23 59»"),
    DELETE_USER_DATA_MESSAGE("Ваши данные полностью удалены"),
    DELETE_USER_DATA_ASK_MESSAGE("Вы уверены, что хотите удалить данные?"),
    YANDEX_ERROR_GET_RESULT_MESSAGE("Ошибка при получении данных Яндекс"),
    YANDEX_RESULT_MESSAGE("Затраты на рекламу в Яндекс директ: "),
    YANDEX_ERROR_GET_TOKEN_MESSAGE("Вы незарегистрированы в Яндекс Директ"),
    SETTINGS_MENU_MESSAGE("В этом меню вы можете изменить время отправки уведомлений, часовой пояс, " +
            "подключить новые аккаунты, отключить уведомления, а также удалить полностью свои данные."),
    TIMER_ADDED_MESSAGE("Таймер установлен на "),
    IN_DEVELOPING_MESSAGE("Эта опция находится в разработке"),
    PROJECT_CREATE_START_MESSAGE("Вы можете создать несколько проектов и подключить в них разные аккаунты рекламных источников и СРМ"),
    PROJECT_CREATE_DONE_MESSAGE("Проект успешно создан"),
    PROJECT_MENU_MESSAGE("Создайте новый, отредактируйте существующий или удалите ненужный проект"),
    PROJECT_CREATE_ASK_NAME_MESSAGE("Введите название проекта, не более 30 символов"),
    SETTINGS_PROJECTS_LIST_MENU_MESSAGE("В этом меню отображены все ваши проекты. Вы можете изменить подключенные источники данных или удалить проект"),
    PROJECT_NAME_INVALID_MESSAGE("Вы ввели некоректное название проекта. Введите название из 30 символов, " +
            "используйте кирилицу или латиницу, а также цифры."),


    INVALID_TIME_MESSAGE("Неверный формат времени. \nВведите время в формате: \n«23 59», \n«00 00». \n\nБез кавычек!"),
    NON_COMMAND_MESSAGE("Вы ввели неизвестную команду"),
    DISABLE_NOTIFICATIONS_MESSAGE("Уведомления отключены"),
    ENABLE_NOTIFICATIONS_MESSAGE("Уведомления включены"),
    NOT_DELETE_USER_DATA_MESSAGE("Мы рады что вы продолжаете пользоваться нашим ботом!"),


    //ошибки при обработке callback-ов
    EXCEPTION_BAD_BUTTON_NAME_MESSAGE("Неверное значение кнопки. Крайне странно. Попробуйте позже"),

    //прочие ошибки
    EXCEPTION_ILLEGAL_MESSAGE("Неверный формат сообщения"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
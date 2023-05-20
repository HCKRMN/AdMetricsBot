package com.kuzmichev.AdMetricsBot.constants;

/**
 * Текстовые сообщения, посылаемые ботом
 */
public enum BotMessageEnum {
    //ответы на команды с клавиатуры
    START_MESSAGE("\uD83D\uDC4B Привет! Этот бот собирает данные по рекламным источникам и срм, " +
            "после чего в заданное время регулярно отправляет сообщение с краткой статистикой. \n\n" +
            "Благодаря этому ты можешь держать руку на пульсе своего бизнеса и принимать своевременные решения!" +
            "Зарегистрируйтесь, чтобы начать работу\uD83D\uDC47"),
    TIME_ZONE_DEFINITION_MESSAGE("Перейдите по ссылке чтобы добавить разницу часовых поясов. Обязательно выключите VPN, если используете его"),
    ADD_YANDEX_MESSAGE("Перейдите по ссылке чтобы подключить аккаунт Яндекс Директ"),
    ADD_TOKENS_MESSAGE("Подключение источников данных"),
    ACCOUNT_ALREADY_ADDED_MESSAGE("Аккаунт Яндекс уже добавлен, хотите подключить другие сервисы?"),
    ASK_TIME_MESSAGE("Укажите время отправки уведомления в формате «23 59»"),
    YANDEX_RESOULT_MESSAGE("Затраты на рекламу в Яндекс директ: "),
    ERRORE_YANDEX_MESSAGE("Вы незарегистрированы или произошла ошибка при получении данных из Яндекс Директ"),
    TIMER_ADDED_MESSAGE("Таймер установлен на "),
    INVALID_TIME_MESSAGE("Неверный формат времени. \nВведите время в формате: \n«23 59», \n«00 00». \n\nБез кавычек!"),
    NON_COMMAND_MESSAGE("Вы ввели неизвестную команду"),


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
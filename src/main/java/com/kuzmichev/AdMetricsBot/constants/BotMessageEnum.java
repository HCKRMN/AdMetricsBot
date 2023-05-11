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
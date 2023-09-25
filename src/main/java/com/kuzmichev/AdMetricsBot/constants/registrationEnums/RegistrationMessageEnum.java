package com.kuzmichev.AdMetricsBot.constants.registrationEnums;

/**
 * Текстовые сообщения, используемые в процессе первичной регистрации
 */
public enum RegistrationMessageEnum {
    START_MESSAGE("""
            \uD83D\uDC4B Привет! Этот бот собирает данные по рекламным источникам и срм, после чего в заданное время регулярно отправляет сообщение с краткой статистикой.\s

            Благодаря этому ты можешь держать руку на пульсе своего бизнеса и принимать своевременные решения! Зарегистрируйтесь, чтобы начать!\uD83D\uDC47"""),
    REGISTRATION_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE("Разница часовых поясов успешно добавлена!\n Теперь необходимо создать проект"),

    REGISTRATION_YANDEX_TEST_MESSAGE("Проверьте получение данных с аккаунта Яндекс Директ и переходите к следующему этапу."),
    ;

    private final String message;

    RegistrationMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

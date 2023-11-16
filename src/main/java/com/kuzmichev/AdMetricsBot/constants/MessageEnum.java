package com.kuzmichev.AdMetricsBot.constants;

/**
 * Текстовые сообщения, посылаемые ботом
 */
public enum MessageEnum {
    SETTINGS_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE("Разница часовых поясов успешно добавлена"),
    EDIT_TIMEZONE_MANUAL_MESSAGE("Укажите ваше текущее время в формате «23 59»"),
    ADD_YANDEX_MESSAGE("Перейдите по ссылке чтобы подключить аккаунт Яндекс Директ"),
    INPUT_TEST_MESSAGE("Проверьте получение данных"),
    ADD_BITRIX_STEP_1_MESSAGE("""
            Перед тем как указать домен, убедитесь, что в маркете вашего портала Битрикс24 установлено приложение AdMetricsBot! \s
            
            Введите домен вашего портала Битрикс24 в формате, без кавычек:\s
            «test.bitrix24.ru».\s
            Если у вас собственный домен без bitrix.ru, введите его."""),
    ADD_BITRIX_STEP_2_MESSAGE("Теперь перейдите по ссылке"),
    ADD_TOKENS_MESSAGE("Добавьте источники данных"),
    ASK_TIME_MESSAGE("Укажите время отправки уведомления в формате «23 59»"),
    DELETE_USER_DATA_ASK_MESSAGE("Вы уверены, что хотите удалить данные?"),
    DELETE_USER_DATA_SUCCESS_MESSAGE("Данные успешно удалены"),
    SETTINGS_MENU_MESSAGE("В этом меню вы можете изменить время отправки уведомлений, часовой пояс, " +
            "подключить новые аккаунты, отключить уведомления, а также удалить полностью свои данные."),
    TIMER_ADDED_MESSAGE("Таймер установлен на "),
    IN_DEVELOPING_MESSAGE("Эта опция находится в разработке"),
    PROJECT_MENU_MESSAGE("Создайте новый, отредактируйте существующий или удалите ненужный проект"),
    SETTINGS_PROJECTS_LIST_MENU_MESSAGE("В этом меню отображены все ваши проекты. Вы можете изменить подключенные источники данных или удалить проект"),
    SETTINGS_PROJECTS_LIST_MENU_ERROR_MESSAGE("Произошла ошибка при получении списка проектов"),
    SETTINGS_SOME_PROJECT_MENU_ERROR_MESSAGE("Произошла ошибка при получении проекта"),
    PROJECT_DELETE_STEP_1_MESSAGE("Вы уверены, что хотите удалить проект?"),
    PROJECT_DELETE_STEP_2_MESSAGE("Проект удалён"),
    PROJECT_INPUT_DELETE_MESSAGE("Выберите источник данных, который хотите удалить"),
    PROJECT_INPUT_DELETE_SUCCESS_MESSAGE("Источник данных успешно удалён"),
    PROJECT_INPUT_DELETE_ERROR_MESSAGE("Произошла ошибка при удалении данных"),

    CONTACT_SAVE_MESSAGE("Контакт успешно сохранен"),


    INVALID_TIME_MESSAGE("Неверный формат времени. \nВведите время в формате: \n«23 59», \n«00 00». \n\nБез кавычек!"),
    INVALID_BITRIX_DOMAIN_MESSAGE("""
            Неверный формат домена. \s
            Введите значение в формате, без кавычек: \s
            «test.bitrix24.ru»
            Если у вас собственный домен без bitrix24.ru, введите его."""),
    NOT_DELETE_USER_DATA_MESSAGE("Я рад что вы продолжаете пользоваться ботом!"),



    //прочие ошибки
    EXCEPTION_ILLEGAL_MESSAGE("Неверный формат сообщения"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так"),

    HELP_MESSAGE("Если у тебя возникли трудности с ботом, пиши @Kuzmichev_Anton"),




    START_MESSAGE("""
            \uD83D\uDC4B Привет! Этот бот собирает данные по рекламным источникам и срм, после чего в заданное время регулярно отправляет сообщение с краткой статистикой.\s

            Благодаря этому ты можешь держать руку на пульсе своего бизнеса и принимать своевременные решения! Зарегистрируйтесь, чтобы начать!\uD83D\uDC47
            
            По всем вопросам пиши @Kuzmichev_Anton"""),
    REGISTRATION_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE("Разница часовых поясов успешно добавлена!\n Теперь необходимо создать проект"),

    REGISTRATION_TEST_INPUTS_MESSAGE("Проверьте получение данных и переходите к следующему этапу."),




    PROJECT_CREATE_ASK_NAME_MESSAGE("Введите название проекта, не более 30 символов"),
    PROJECT_NAME_INVALID_MESSAGE("Вы ввели некорректное название проекта. Введите название из 30 символов, " +
            "используйте кириллицу или латиницу, а также цифры."),
    TIME_ZONE_DEFINITION_MESSAGE("Перейдите по ссылке чтобы добавить разницу часовых поясов. " +
            "Обязательно выключите VPN, если используете его. \n" +
            "Вы также можете добавить разницу часовых поясов вручную."),
    TIMER_ADD_ERROR_MESSAGE("""
            Вы не установили часовой пояс!\s
            Нажмите на кнопку чтобы добавить разницу часовых поясов.\s
            """),

    YANDEX_ERROR_513_MESSAGE("Необходимо закончить регистрацию в Яндекс Директ"),
    YANDEX_ERROR_53_MESSAGE("Токен обращения к Яндекс Директ ошибочный. Необходимо заново выполнить подключение"),
    YANDEX_ERROR_UNKNOWN_MESSAGE("Ошибка получения данных от Яндекса"),

    ERROR_PROJECT_ID_NULL_MESSAGE("Потерян id проекта, подключите источник заново"),


    NON_COMMAND_MESSAGE("Вы ввели неизвестную команду"),

    YCLIENTS_NON_PROJECTS_MESSAGE("Перед тем как установить приложение в маркете Yclients необходимо создать проект. " +
            "Удалите приложение из маркета, создайте проект в боте и следуйте дальнейшим инструкциям."),
    YCLIENTS_ERROR_DECODE_MESSAGE("Ошибка декодирования данных"),

    ADD_YCLIENTS_STEP_1_MESSAGE("Чтобы подключить Yclients необходимо указать свой номер телефона. \n" +
            "Нажмите кнопку «Отправить» для подтверждения номера"),
    ADD_YCLIENTS_STEP_2_MESSAGE("Теперь перейдите по ссылке и установите приложение из маркета Yclients"),
    PHONE_INPUT_ERROR_MESSAGE("Нужно нажать кнопку «Отправить»"),



    ;
    private final String message;

    MessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
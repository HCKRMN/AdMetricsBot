# **TODO**

1) ~~Прикрутить Bitrix и настроить получение данных~~
2) ~~Отказаться от тестового API Yandex.Direct~~
3) ~~Подключить базу данных~~
4) Настроить резервное копирование
5) ~~Сохранить параметры пользователя в базу данных и реализовать регистрацию~~
6) ~~Реализовать авторизацию в Yandex.Direct~~
7) ~~Добавить логирование~~
8) ~~Исправить ошибку валидации Hibernate или реализовать валидацию~~
9) ~~Перейти на региональный домен~~
10) ~~Загрузить приложение в Docker~~
11) ~~Переключиться на PostgreSQL~~
12) ~~Добавить сообщение о необходимости регистрации для функционирования~~
13) ~~Добавить возможность отвязки аккаунта и удаления данных~~
14) ~~Добавить возможность простого отключения уведомлений~~
15) ~~Вынести все ключи API в отдельный файл и изучить лучшие практики~~
16) ~~Синхронизация времени и привязка к географическому местоположению (часовым поясам)~~
17) ~~Рассмотреть вопрос о многопоточности и параллельном получении данных~~
18) ~~Разработать основную логику~~
19) ~~Поддержка нескольких аккаунтов~~
20) ~~Настройка регулярных уведомлений~~
21) Определение валюты и пересчет источников
22) ~~Проверить ошибку пустого аккаунта в Яндекс.Директ~~
23) ~~Автоматическая переадресация при получении токена Яндекса~~
24) ~~Автоматическое закрытие браузера после ссылки (не работает везде)~~
25) ~~Добавить отображение количества продаж (успешных сделок) из Bitrix~~
26) ~~Использовать Spring Data JPA~~
27) ~~Убрать использование Lombok~~
28) ~~Улучшить безопасность данных (SSL, шифрование базы данных)~~
29) ~~Привязать домен к Docker или серверу~~
30) ~~Проверить список дел на наличие дублей~~
31) ~~Обработать исключение при получении токена~~
32) Доработать авторизацию Яндекса
33) ~~Протестировать общее сообщение всем пользователям~~
34) Добавить индексную страницу
35) Создать админ-панель для просмотра статистики
36) ~~Удалять данные по отдельности, например только из Яндекса~~
37) ~~Убрать переменные из кода (возможно, использовать хардкод)~~
38) ~~Структурировать методы в Telegram боте и провести рефакторинг~~
39) ~~Сделать пробный показ (тестирование функционала)~~
40) Добавить очистку кнопок в чате после нажатия
41) ~~Починить Bitrix, зарегистрировать приложение~~
42) Подключить AmoCRM
43) Подключить VK
44) Подключить Yclients
45) Подключить MyTarget
46) Добавить возможность оставлять отзывы
47) Реализовать бан плохих пользователей
48) ~~Если не подключен ни один сервис, не отправлять уведомления, добавить проверку всех сервисов~~
49) ~~Разделить регистрацию и отправку сообщений на микросервисы~~
50) ~~Заранее рассчитывать данные в 00:00? Реализован кэш на каждый час~~
51) ~~Убедиться в корректности времени~~
52) ~~Учесть случаи, если время указано~~
53) ~~Реализовать отправку информации по часам~~
54) ~~Добавить комментарии к коду~~
55) ~~Внедрить кэш состояния пользователя (для оптимизации работы)~~
56) ~~Рассмотреть резервное получение часового пояса, если необходимо~~
57) ~~Инициализировать статус пользователя в случае отсутствия кэша или других проблем~~
58) ~~Добавить функцию изменения планировщика при добавлении нового пользователя (отменено)~~
59) ~~Добавить задержку через сеттер~~
60) ~~Улучшить метод отправки сообщений~~
61) ~~Удаление предыдущего сообщения при запросе информации от пользователя~~
62) ~~Создать в базе список на удаление старых сообщений~~
63) ~~Провести рефакторинг кода и обработать его~~
64) ~~Учесть возможные изменения состояния пользователя, если кэш отсутствует~~
65) ~~Реализовать механизм восстановления после сбоев и уведомление о проблемах~~
66) ~~Изменение планировщика для новых пользователей~~
67) ~~Разгрузить длительные запросы в микросервисы~~
68) ~~Улучшить метод отправки сообщений~~
69) ~~Улучшить логику удаления сообщений и обработки команд~~
70) ~~Рассмотреть оптимизацию работы с листом сообщений~~
71) Обновить логирование и комментарии в коде
72) Реализовать разное время для разных проектов
73) ~~Проверить уникальность имен проектов пользователя~~
74) ~~Добавить обработку команды /help~~
75) Найти проект по его названию
76) Вывести список всех проектов в текстовом формате
77) ~~Добавить динамическую кнопку "удалить источник"~~
78) Подключить логины в Яндексе
79) Переделать метод обновления токена в Bitrix
80) Оптимизировать запросы к базе данных при использовании страниц в списке проектов
81) ~~Подумать о счетчике проектов в базе данных~~
82) ~~Обработать ошибки Bitrix: ERROR: ACCESS_DENIED; ERROR_OAUTH: Application not installed~~
83) Исправить ошибку при вводе домена Bitrix: [400] Bad Request: message identifier is not specified
84) ~~Улучшить возврат данных в StateHandler методе~~
85) ~~Реализовать безопасное хранение паролей и настроить их в Git~~
86) ~~Решить проблему с ошибкой при нажатии на кнопку настроек без регистрации~~
87) ~~Решить проблему с ошибкой NullPointerException~~
88) ~~Решить проблему с ошибкой Bad Request при вводе домена Bitrix~~
89) ~~Проверить необходимость дополнительных изменений в состоянии пользователя в callback'ах~~
90) ~~Доработать сообщение об установке Bitrix или автоматизировать этот процесс~~
91) ~~Улучшить определение времени пользователя и сервера~~
92) ~~Обработать статусы и выполнить тестовый запрос к Yclients~~
93) ~~Настроить подключение Yclients~~
94) ~~Написать тесты проверки Enum~~
95) Добавить в сообщение информацию за последний день, за 3 дня, за 7 дней
96) ~~Избавиться от временных костылей~~
97) ~~Убрать условия типа if (data.equals), разделить handleCallback~~
98) ~~Избегать использования Object.equals~~
99) ~~Использовать userState.contains(StateEnum.REGISTRATION.getStateName()) везде, где это необходимо~~
100) В MessageBuilder проверить наличие данных, возможно, создать две секции (для CRM и рекламы) и проверять каждую отдельно
101) Реализовать отключение проектов
102) ~~Исправить кнопку "получить данные"~~
103) Вернуть меню проекта после удаления источника
104) Реализовать восстановление после сбоев и уведомление о проблемах
105) Обеспечить шифрование базы данных
106) Возможно стоит разделить ежедневные сообщения по проектам
107) Главная переадресовывает в телегу
108) Регистрация через ссылку yclients 
109) ~~Избавиться от запроса номера телефона при подключении yclients~~
110) Исправить клавиатуру bitrix
111) доделать регистрацию через yclients
112) Проверить ошибку нескольких yclients без projectId, если юзер начал регистрацию нового yclients, но не закончил старую
113) Лишние отступы при тесте
114) Пить пиво

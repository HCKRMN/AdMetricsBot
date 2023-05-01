package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.config.BotConfig;
import com.kuzmichev.AdMetricsBot.model.*;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BiRepository biRepository;
    @Autowired
    private ScheduledMessageRepository scheduledMessageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private YaRepository yaRepository;
    final BotConfig config;

    static final String ERROR_TEXT = "Error occurred: ";
    private static final String ADD_YA_BUTTON = "ADD_YA_BUTTON";
    private static final String ADD_BI_BUTTON = "ADD_BI_BUTTON";
    private static final String YES_ADD = "YES_ADD";
    private static final String NO_CONTINUE = "NO_CONTINUE";
    private static final String TEST_YA = "TEST_YA";

    public TelegramBot(BotConfig config){
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","Запуск"));
        listOfCommands.add(new BotCommand("/help","Помощь"));
        listOfCommands.add(new BotCommand("/register","Регистрация"));
        listOfCommands.add(new BotCommand("/test","Тестовый запуск"));
        listOfCommands.add(new BotCommand("/settings","Настройки"));
        listOfCommands.add(new BotCommand("/launch","Запуск"));
        listOfCommands.add(new BotCommand("/delete","Удаление данных"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(),null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's list:" + e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {return config.getBotName();}
    @Override
    public String getBotToken() {return config.getToken();}
    public String getYaClientID() {return config.getClientID();}
    public String getYaRedirectURI() {return config.getRedirectURI();}

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if(messageText.contains("/send") && config.getOwnerId() == chatId) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = userRepository.findAll();
                for (User user : users) {
                    sendMessage(user.getChatId(), textToSend);
                }
            } else if (messageText.matches("\\d{2} \\d{2}")) {
                setTimerAndStart(chatId, messageText);
            } else{
                switch (messageText) {
                        case "/start" ->
                            startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        case "/register" -> {
                            sendMessage(chatId, "Начинаем регистрацию..");
                            registerUser(update.getMessage());
                            addTokens(chatId);
                        }
                        case "/test" ->
                            TestYaData(chatId);
                        default -> sendMessage(chatId, "Хмм, похоже произошла ошибка.");
                }
            }
        }   else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case ADD_YA_BUTTON ->
                    findYaData(chatId);
                case ADD_BI_BUTTON -> {
                    sendMessage(chatId, "Начинаем подключать Bitrix24..");
                    addBiData();}
                case YES_ADD ->
                    addTokens(chatId);
                case NO_CONTINUE ->
                    askTime(chatId);
                case TEST_YA ->
                    TestYaData(chatId);
            }
        }
    }
    private void setTimerAndStart(long chatId, String messageText) {
        LocalTime timerMessage = LocalTime.
                parse(messageText.replace(" ", ":")).
                plusHours(userRepository.findById(chatId).get().getTimeZone());
        System.out.println("Время для заноса в базу" + timerMessage);
//        String timerMessage = messageText.replace(" ", ":");
        YaData yaData = yaRepository.findById(chatId).orElse(null);
        System.out.println(yaData);
        if (yaData != null && yaData.getYaToken() != null) {


            ScheduledMessage scheduledMessage = new ScheduledMessage();
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setTimerMessage(String.valueOf(timerMessage));
            scheduledMessage.setEnableSendingMessages(true);
            scheduledMessageRepository.save(scheduledMessage);



//            scheduledMessageRepository
//                    .findById(chatId)
//                    .ifPresent(scheduledMessage -> {
//                        scheduledMessage.setChatId(chatId);
//                        scheduledMessage.setTimerMessage(String.valueOf(timerMessage));
//                        scheduledMessage.setEnableSendingMessages(true);
//                        scheduledMessageRepository.save(scheduledMessage);
//                });
        sendMessage(chatId, "Таймер установлен на " + timerMessage + ". Текущее время: " + LocalTime.now());
        log.info("User set timer at " + timerMessage);

        } else {
            sendMessage(chatId, "Добавьте хотя-бы один сервис");
        }
    }

    private void TestYaData(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        try {
            message.setText("Затраты на рекламу в Яндекс директ: " + YandexDirectRequest.ya(yaRepository, chatId));
            executeMessage(message);
        } catch (Exception e) {
            message.setText("Вы не зарегистрированы");
            executeMessage(message);
            log.error("Error setting bot's list:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void askTime(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Укажите время отправки уведомления в формате «23 59»");
        executeMessage(message);
    }

    private void findYaData(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        YaData yaData = yaRepository.findById(chatId).orElse(null);
        if (yaData != null && yaData.getYaToken() != null) {
            message.setText("Аккаунт Яндекс уже добавлен, хотите подключить другие сервисы?");

            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();

            var yesAdd = new InlineKeyboardButton();
            yesAdd.setText("Да, добавить");
            var noCon = new InlineKeyboardButton();
            noCon.setText("Нет, продолжить");

            rowInLine.add(yesAdd);
            yesAdd.setCallbackData(YES_ADD);
            rowInLine.add(noCon);
            noCon.setCallbackData(NO_CONTINUE);
            rowsInLine.add(rowInLine);
            markupInLine.setKeyboard(rowsInLine);
            message.setReplyMarkup(markupInLine);
            executeMessage(message);
        } else {
            addYaData(chatId);
            preTestYaData(chatId);
        }
    }

    private void preTestYaData(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Протестируйте получение данных с Яндекса");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var testYa = new InlineKeyboardButton();
        testYa.setText("Тест");

        rowInLine.add(testYa);
        testYa.setCallbackData(TEST_YA);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        executeMessage(message);
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет! " +name +" Этот бот собирает расходы по рекламным источникам за прошлый день, " +
                "а также берет информацию по лидам из срм и выдаёт раз в сутки сообщение с этими данными. " +
                "Благодаря чему, ты можешь держать руку на пульсе своего бизнеса!");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void registerUser(Message msg) {
        sendMessage(msg.getChatId(), "Регистрируем нового пользователя..");



        if(userRepository.findById(msg.getChatId()).isEmpty()){
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            long unixTimeMessage = msg.getDate();
            Instant instant = Instant.ofEpochSecond(unixTimeMessage);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            int userHours = Integer.parseInt(localDateTime.format(DateTimeFormatter.ofPattern("HH")));
            LocalTime now = LocalTime.now();
            int serverHours = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("HH")));
            int userTimeZone = serverHours - userHours;

            User user = new User();
            ScheduledMessage scheduledMessage = new ScheduledMessage();
            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            scheduledMessage.setChatId(chatId);
            scheduledMessage.setEnableSendingMessages(false);
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            user.setTimeZone(userTimeZone);
            userRepository.save(user);
            scheduledMessageRepository.save(scheduledMessage);
            log.info("user saved: " + user);
        }
    }
    private void addTokens(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Подключение каналов");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var addYa = new InlineKeyboardButton();
        addYa.setText("Яндекс Директ");
        var addBi = new InlineKeyboardButton();
        addBi.setText("Bitrix24");

        rowInLine.add(addYa);
        addYa.setCallbackData(ADD_YA_BUTTON);
        rowInLine.add(addBi);
        addBi.setCallbackData(ADD_BI_BUTTON);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        executeMessage(message);
    }

    private void addYaData(long chatId){
        String clientId = getYaClientID();
        String redirectUri = getYaRedirectURI();
        String state = String.valueOf(chatId);

        String yaAuthorizationUrl = "https://oauth.yandex.ru/authorize" +
                "?response_type=token" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Нажмите на ссылку");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button = new InlineKeyboardButton();
        button.setText("ССЫЛКА");
        button.setUrl(yaAuthorizationUrl);

        rowInLine.add(button);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        executeMessage(message);

    }
    private void addBiData(){

    }

    protected void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }
    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
}

package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.config.BotConfig;
import com.kuzmichev.AdMetricsBot.model.BiRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.Value;
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
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BiRepository biRepository;
    @Autowired
    private YaRepository yaRepository;
    final BotConfig config;

    static final String ERROR_TEXT = "Error occurred: ";
    private static final String ADD_YA_BUTTON = "ADD_YA_BUTTON";
    private static final String ADD_BI_BUTTON = "ADD_BI_BUTTON";

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
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

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
            }
            else {
                switch (messageText) {
                        case "/start" -> {
                            startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    }
                        case "/register" -> {
                            sendMessage(chatId, "Начинаем регистрацию..");
                            registerUser(update.getMessage());
                            addTokens(chatId);
                        }
                        case "/test" -> {
                            try {
                                sendMessage(chatId, "Затраты на рекламу в Яндекс директ логин (вставить сюда логин): " + YandexDirectRequest.ya());
                                sendMessage(chatId, "Количество лидов (вставить сюда домен битрикса):\n " + BitrixRequest.bi());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                        default -> sendMessage(chatId, "Хмм, похоже произошла ошибка.");
                }
            }
        }   else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case ADD_YA_BUTTON: {
                    sendMessage(chatId,"Начинаем подключать Яндекс директ..");
                    addYaData(chatId);
                    break;
                }
                case ADD_BI_BUTTON: {
                    sendMessage(chatId,"Начинаем подключать Bitrix24..");
                    addBiData();
                    break;
                }
            }
        }
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
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
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
        addYa.setText("Добавить аккаунт Яндекс Директ");
        var addBi = new InlineKeyboardButton();
        addBi.setText("Добавить аккаунт Bitrix24");

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
        String clientId = "7e39208ce43e4a2aab9d4901f120ee39";
        String redirectUri = "https://admetricsbot.ru/ya-redirect";
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

    private void sendMessage(long chatId, String textToSend) {
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

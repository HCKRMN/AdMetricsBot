package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    YandexDirectExample yandexDirectExample = new YandexDirectExample();

    static final String ERROR_TEXT = "Error occurred: ";
    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
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
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();


            switch (messageText) {
                case "/start" -> sendMessage(chatId, "Привет! Этот бот собирает расходы по рекламным источникам за прошлый день, а также берет информацию по лидам из срм и выдаёт раз в сутки сообщение с этими данными. \n Благодаря чему, ты можешь держать руку на пульсе своего бизнеса! Нажмите /test чтобы протестировать");
                case "/test" -> {
                                try {
                                    sendMessage(chatId,"Затраты на рекламу в Яндекс директ: " +yandexDirectExample.ya());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                }
                default -> sendMessage(chatId, "Хмм, похоже произошла ошибка.");
            }
        }
//        else if (update.hasCallbackQuery()) {
//            String callbackData = update.getCallbackQuery().getData();
//            long messageId = update.getCallbackQuery().getMessage().getMessageId();
//            long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//            switch (callbackData) {
//                case YES_BUTTON: {
//                    sendMessage(chatId,"?");
//                    break;
//                }
//            }
//        }
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

package com.kuzmichev.AdMetricsBot.config;

import com.kuzmichev.AdMetricsBot.telegram.AdMetricsBot;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.MainCallbackQueryHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public AdMetricsBot springWebhookBot(SetWebhook setWebhook,
                                         MessageHandler messageHandler,
                                         MainCallbackQueryHandler mainCallbackQueryHandler) {
        AdMetricsBot bot = new AdMetricsBot(setWebhook, messageHandler, mainCallbackQueryHandler);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }
}
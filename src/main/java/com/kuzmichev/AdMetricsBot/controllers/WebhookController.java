package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.telegram.AdMetricsBot;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
public class WebhookController {
    private final AdMetricsBot adMetricsBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return adMetricsBot.onWebhookUpdateReceived(update);
    }
}
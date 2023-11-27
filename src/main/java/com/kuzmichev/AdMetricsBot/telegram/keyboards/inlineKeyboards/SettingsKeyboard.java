package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.ScheduledMessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SettingsKeyboard implements InlineKeyboard{
    ScheduledMessageRepository scheduledMessageRepository;

    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {

        String launchButton;
        CallBackEnum launchCallback;
        boolean state = scheduledMessageRepository.findEnableSendingMessagesByChatId(chatId);
        if (state) {
            launchButton = ButtonEnum.SETTINGS_DISABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = CallBackEnum.DISABLE_NOTIFICATIONS_CALLBACK;
        } else {
            launchButton = ButtonEnum.SETTINGS_ENABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = CallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK;
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.PROJECTS_BUTTON.getButtonName())
                                .callbackData(CallBackEnum.PROJECTS_CALLBACK.getCallBackName())
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(launchButton)
                                .callbackData(launchCallback.getCallBackName())
                                .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.SETTINGS_EDIT_TIMEZONE_BUTTON.getButtonName())
                                .callbackData(CallBackEnum.EDIT_TIMEZONE_CALLBACK.getCallBackName())
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(ButtonEnum.SETTINGS_EDIT_TIMER_BUTTON.getButtonName())
                                .callbackData(CallBackEnum.EDIT_TIMER_CALLBACK.getCallBackName())
                                .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.SETTINGS_EDIT_LANGUAGE_BUTTON.getButtonName())
                                .callbackData(CallBackEnum.EDIT_LANGUAGE_CALLBACK.getCallBackName())
                                .build(),
                        InlineKeyboardButton.builder()
                                .text(ButtonEnum.SETTINGS_DELETE_USER_BUTTON.getButtonName())
                                .callbackData(CallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())
                                .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                                .text(ButtonEnum.SETTINGS_EXIT_BUTTON.getButtonName())
                                .callbackData(CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName())
                                .build()))
                .build();
    }
}

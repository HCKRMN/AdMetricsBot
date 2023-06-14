package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStatesEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SettingsMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    UserRepository userRepository;

    public SendMessage menuMaker(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage());

        String launchButton;
        CallBackEnum launchCallback;
        String state = userRepository.getUserStateByChatId(chatId);
        System.out.println(state);
        if (state.equals(UserStatesEnum.START_NOTIFICATIONS_STATE.getStateName())){
            launchButton = ButtonNameEnum.SETTINGS_DISABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = CallBackEnum.DISABLE_NOTIFICATIONS_CALLBACK;
        } else {
            launchButton = ButtonNameEnum.SETTINGS_ENABLE_NOTIFICATIONS_BUTTON.getButtonName();
            launchCallback = CallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK;
        }

        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                                launchButton,
                                                launchCallback,
                                                null
                                        ),
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_EDIT_TIMEZONE_BUTTON.getButtonName(),
                                                CallBackEnum.EDIT_TIMEZONE_CALLBACK,
                                                null
                                        )
                                ),
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.SETTINGS_EDIT_LANGUAGE_BUTTON.getButtonName(),
                                            CallBackEnum.EDIT_LANGUAGE_CALLBACK,
                                            null
                                        ),
                                        inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.SETTINGS_EDIT_TIMER_BUTTON.getButtonName(),
                                            CallBackEnum.EDIT_TIMER_CALLBACK,
                                            null
                                        )
                                ),
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                            ButtonNameEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                            CallBackEnum.ADD_ACCOUNTS_CALLBACK,
                                            null
                                        ),
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.SETTINGS_DELETE_USER_BUTTON.getButtonName(),
                                                CallBackEnum.DELETE_USER_CALLBACK,
                                                null
                                        )
                                )
                        )
                )
        );
        return sendMessage;
    }
}

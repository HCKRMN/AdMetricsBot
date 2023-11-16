package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu;

import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.deleteMenu.DeleteUserStepOneCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.deleteMenu.DeleteUserStepTwoCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.deleteMenu.NotDeleteUserCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.notifications.DisableNotificationsCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.notifications.EnableNotificationsCallbackHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MenuCallbackHandlersList {
    BackCallbackHandler backCallbackHandler;
    DeleteUserStepOneCallbackHandler deleteUserStepOneCallbackHandler;
    DeleteUserStepTwoCallbackHandler deleteUserStepTwoCallbackHandler;
    NotDeleteUserCallbackHandler notDeleteUserCallbackHandler;
    EditLanguageCallbackHandler editLanguageCallbackHandler;
    EditTimerCallbackHandler editTimerCallbackHandler;
    EnableNotificationsCallbackHandler enableNotificationsCallbackHandler;
    ExitCallbackHandler exitCallbackHandler;
    DisableNotificationsCallbackHandler disableNotificationsCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        return Arrays.asList(
                backCallbackHandler,
                notDeleteUserCallbackHandler,
                editLanguageCallbackHandler,
                editTimerCallbackHandler,
                enableNotificationsCallbackHandler,
                disableNotificationsCallbackHandler,
                exitCallbackHandler,
                deleteUserStepOneCallbackHandler,
                deleteUserStepTwoCallbackHandler
        );
    }
}

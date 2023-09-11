package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Menu;

import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MenuCallbackHandlersList {
    BackAndExitCallbackHandler backAndExitCallbackHandler;
    DeleteUserCallbackHandler deleteUserCallbackHandler;
    EditLanguageCallbackHandler editLanguageCallbackHandler;
    EditTimerCallbackHandler editTimerCallbackHandler;
    NotificationControllerCallbackHandler notificationControllerCallbackHandler;
    TimeZoneMenuCallbackHandler timeZoneMenuCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        List<CallbackHandler> callbackHandlers = new ArrayList<>();
        callbackHandlers.add(backAndExitCallbackHandler);
        callbackHandlers.add(deleteUserCallbackHandler);
        callbackHandlers.add(editLanguageCallbackHandler);
        callbackHandlers.add(editTimerCallbackHandler);
        callbackHandlers.add(notificationControllerCallbackHandler);
        callbackHandlers.add(timeZoneMenuCallbackHandler);
        return callbackHandlers;
    }
}

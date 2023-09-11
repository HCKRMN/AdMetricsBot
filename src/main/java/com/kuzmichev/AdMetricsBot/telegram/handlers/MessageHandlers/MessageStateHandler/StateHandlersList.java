package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StateHandlersList {
    List<StateHandler> stateHandlers;

    public StateHandlersList(
            AskProjectNameStateHandler askProjectNameStateHandler,
            BitrixDomainStateHandler bitrixDomainStateHandler,
            EditTimerStateHandler editTimerStateHandler,
            ManualEditTimeZoneStateHandler manualEditTimeZoneStateHandler
    ) {
        stateHandlers = new ArrayList<>();
        stateHandlers.add(askProjectNameStateHandler);
        stateHandlers.add(bitrixDomainStateHandler);
        stateHandlers.add(editTimerStateHandler);
        stateHandlers.add(manualEditTimeZoneStateHandler);
    }

    public List<StateHandler> getStateHandlers() {
        return stateHandlers;
    }
}

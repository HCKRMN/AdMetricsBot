package com.kuzmichev.AdMetricsBot.telegram.utils.TempData;

import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserStateKeeper {
    HashMap<String, String> userState = new HashMap<>();
    public void setState(String chatId, String state) {
        log.info("Меняем состояние у юзера {}, состояние: {}", chatId, state);
        userState.put(chatId, state);
    }
    public String getState(String chatId){
        if(userState.containsKey(chatId)){
            String userStateName = userState.get(chatId);
            log.info("Получаем состояние юзера {} : {}", chatId, userStateName);
            return userStateName;
        }
        log.info("У юзера {} не нашлось состояния, возвращаем регистрацию", chatId);
        return StateEnum.REGISTRATION_STATE.getStateName();
    }
}

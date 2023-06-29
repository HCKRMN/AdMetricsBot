package com.kuzmichev.AdMetricsBot.telegram.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Validator {

    public boolean validateTime(String messageText) {
    // Валидируем чтобы время было в формате 23 59
        return messageText.matches("((?:[01]\\d|2[0-3]) (?:[0-5]\\d))|((?:[0-9]|1\\d|2[0-3]) (?:[0-5]\\d))\n");
    }

    public boolean validateProjectName(String projectName) {
        // Проверка длины имени проекта (не более 30 символов)
        if (projectName.length() > 30) {
            return false;
        }

        // Проверка наличия только латинских и кириллических букв, цифр и пробелов
        String regex = "^[a-zA-Zа-яА-Я0-9\\s]+$";
        if (!projectName.matches(regex)) {
            return false;
        }

        return true;
    }

}

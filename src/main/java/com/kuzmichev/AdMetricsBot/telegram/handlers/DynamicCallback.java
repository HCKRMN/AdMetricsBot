package com.kuzmichev.AdMetricsBot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DynamicCallback {
    public static Map<String, String> handleDynamicCallback(String data, String regex, String prefix) {
        Map<String, String> dynamicData = new HashMap<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            int startIndex = data.indexOf(prefix) + prefix.length();
            String dynamicValue = data.substring(startIndex);
            dynamicData.put("prefix", prefix);
            dynamicData.put("value", dynamicValue);
        }

        return dynamicData;
    }
}

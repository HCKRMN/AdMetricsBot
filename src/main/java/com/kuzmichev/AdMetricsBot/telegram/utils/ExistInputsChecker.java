package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ExistInputsChecker {
    BitrixRepository bitrixRepository;
    YandexRepository yandexRepository;
    YclientsRepository yclientsRepository;

    public boolean isExist(String projectId) {
        return bitrixRepository.existsByProjectId(projectId) ||
                yandexRepository.existsByProjectId(projectId) ||
                yclientsRepository.existsByProjectId(projectId);

    }
}

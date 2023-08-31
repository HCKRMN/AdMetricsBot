package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.InputsEnum;
import com.kuzmichev.AdMetricsBot.model.*;
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
public class InputsManager {
    TempDataRepository tempDataRepository;
    ProjectRepository projectRepository;
    YandexRepository yandexRepository;
    BitrixRepository bitrixRepository;

    public void deleteInputs(String projectId, String inputName) {

        System.out.println("projectId: " + projectId + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        switch (InputsEnum.valueOf(inputName)) {
            case Yandex -> {
                yandexRepository.removeYandexByProjectId(projectId);
            }
            case Bitrix24 -> {
                bitrixRepository.removeBitrixByProjectId(projectId);
            }
        }

    }

    public List<InputsEnum> getInputsByProjectId(String projectId) {
        List<InputsEnum> inputs = new ArrayList<>();

        if(yandexRepository.existsByProjectId(projectId)) {
            inputs.add(InputsEnum.Yandex);
        }
        if(bitrixRepository.existsByProjectId(projectId)) {
            inputs.add(InputsEnum.Bitrix24);
        }

        return inputs;

    }









}
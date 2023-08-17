package com.kuzmichev.AdMetricsBot.constants;

public enum InputsEnum {
    Yandex("Yandex"),
    VK("VK"),
    MyTarget("MyTarget"),
    Bitrix24("Bitrix24"),
    AmoCRM("AmoCRM"),
    Yclients("Yclients"),;

    private final String inputName;

    InputsEnum(String callBackName) {
        this.inputName = callBackName;
    }

    public String getInputName() {
        return inputName;
    }
}

package com.kuzmichev.AdMetricsBot.constants;

public enum CommandEnum {
    START("/start"),
    SETTINGS("/settings"),
    HELP("/help"),
    REGISTER("/register"),
    TEST("/test"),
    ;

    private final String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandEnum fromCommand(String command) {
        for (CommandEnum value : CommandEnum.values()) {
            if (value.getCommand().equalsIgnoreCase(command) || ("/" + value.getCommand()).equalsIgnoreCase(command)) {
                return value;
            }
        }
        return null;
    }


}


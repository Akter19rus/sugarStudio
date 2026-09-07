package com.example.sugarStudioBot.bot.command.adminCommand;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdminCommandName {

    BIKINI("Бикини"),
    ADMIN_FORWARD_MESSAGE("Разослать сообщение"),
    CONFIRM("Подтвердить");

    private final String adminCommandName;
}

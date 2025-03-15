package com.example.sugarStudioBot.bot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandName {

    START("/start"),
    ABOUT_ME("Обо мне"),
    PRISE("Прайс/запись"),
    REVIEW("Отзывы"),
    ADDRESS("Адрес студии"),
    CONTACT("Мои контакты"),
    WORKS("Мои работы"),
    MAIN_MENU("Главное меню"),
    LEAVE_REVIEW("Оставить отзыв");

    private final String commandName;
}

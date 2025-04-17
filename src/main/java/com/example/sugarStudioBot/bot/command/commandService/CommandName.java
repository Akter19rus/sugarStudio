package com.example.sugarStudioBot.bot.command.commandService;

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
    LEAVE_REVIEW("Оставить отзыв"),
    SIGN_UP("Сделать запись"),
    ADMIN_FORWARD_MESSAGE("Разослать сообщение"),
    CONFIRM("Подтвердить");

    private final String commandName;
}

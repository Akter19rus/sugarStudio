package com.example.sugarStudioBot.bot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface InstallKeyboard {
    List<List<InlineKeyboardButton>> mainMenu();

    List<List<InlineKeyboardButton>> backToMainMenu();

    List<List<InlineKeyboardButton>> review();

    List<List<InlineKeyboardButton>> signUp();
}

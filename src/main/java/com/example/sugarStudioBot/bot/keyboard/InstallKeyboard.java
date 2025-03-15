package com.example.sugarStudioBot.bot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface InstallKeyboard {
    public List<List<InlineKeyboardButton>> mainMenu();
    public List<List<InlineKeyboardButton>> backToMainMenu();
    public List<List<InlineKeyboardButton>> review();
}

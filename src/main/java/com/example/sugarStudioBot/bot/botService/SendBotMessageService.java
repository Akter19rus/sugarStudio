package com.example.sugarStudioBot.bot.botService;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SendBotMessageService {

    void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);
}

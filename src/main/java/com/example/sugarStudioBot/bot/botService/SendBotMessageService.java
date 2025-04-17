package com.example.sugarStudioBot.bot.botService;

import com.example.sugarStudioBot.service.model.Images;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface SendBotMessageService {

    void sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendMessagePhoto(long chatId, String message, List<Images> photoPath, InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendCopyMessageFromAdmin(long chatId, Message message);

    void sendCopyMediaGroupFromAdmin(long chatId, long chatIdAdmin, List<Integer> messageIds);
}

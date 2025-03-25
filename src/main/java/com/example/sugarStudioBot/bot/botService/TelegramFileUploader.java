package com.example.sugarStudioBot.bot.botService;

import org.json.JSONException;

import java.io.IOException;

public interface TelegramFileUploader {
    String uploadPhoto(long chatId, String filePath, String token) throws IOException, JSONException;
}

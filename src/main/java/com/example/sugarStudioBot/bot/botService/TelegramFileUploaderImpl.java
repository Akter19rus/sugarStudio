package com.example.sugarStudioBot.bot.botService;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class TelegramFileUploaderImpl implements TelegramFileUploader {

    public String uploadPhoto(long chatId, String filePath, String token) throws IOException, JSONException {
        String TELEGRAM_API_URL = "https://api.telegram.org/bot" + token + "/sendPhoto";
        File file = new File(filePath);
        if (!file.exists()) {
            log.error("Файл не найден: " + filePath);
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", String.valueOf(chatId))
                .addFormDataPart("photo", file.getName(),
                        RequestBody.create(MediaType.parse("image/"), file))
                .build();

        Request request = new Request.Builder()
                .url(TELEGRAM_API_URL)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Ошибка при загрузке фото: " + response.code() + " " + response.body().string());
            }
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            if (json.has("result") && json.getJSONObject("result").has("photo")) {
                JSONArray photoArray = json.getJSONObject("result").getJSONArray("photo");
                return photoArray.getJSONObject(photoArray.length() - 1).getString("file_id");
            } else {
                throw new JSONException("Неожиданный формат ответа от Telegram API: " + responseBody);
            }
        }
    }
}

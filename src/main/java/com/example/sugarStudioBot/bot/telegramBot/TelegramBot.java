package com.example.sugarStudioBot.bot.telegramBot;

import com.example.sugarStudioBot.bot.configuration.InfoBotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final InfoBotConfiguration config;

    public TelegramBot(InfoBotConfiguration config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Получили сообщение");
        try {
            if (update.hasMessage() && update.getMessage().hasText() || update.hasCallbackQuery() || update.getMessage().hasPhoto()) {
                log.info("пришел текст / нажата кнопка / пришло фото");
            }
        } catch (Exception e) {
            log.error("Ошибка в методе onUpdateReceived: " + e.getMessage());
        }

    }
}

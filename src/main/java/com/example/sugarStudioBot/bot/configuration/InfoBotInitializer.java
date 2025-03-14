package com.example.sugarStudioBot.bot.configuration;

import com.example.sugarStudioBot.bot.telegramBot.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@AllArgsConstructor
@Slf4j
@Component
public class InfoBotInitializer {

    private final TelegramBot telegramBot;

    /**
     * инициализация телеграм бота
     *
     * @throws TelegramApiException проблемы с инициализацией
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init(ContextRefreshedEvent event) throws TelegramApiException {
        log.info("Процесс инициализации - " + event);
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try {
            api.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            log.error("Ошибка инициализации в методе: " + e.getMessage());
        }
    }
}

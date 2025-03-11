package com.example.sugarStudioBot.bot.configuration;

import com.example.sugarStudioBot.bot.telegramBot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class InfoBotInitializer {

    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    public InfoBotInitializer(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * инициализация телеграм бота
     *
     * @throws TelegramApiException
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init(ContextRefreshedEvent event) throws TelegramApiException {
        logger.info("Процесс инициализации ");
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        try {
            api.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            logger.error("Ошибка инициализации в методе: " + e.getMessage());
        }
    }
}

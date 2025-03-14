package com.example.sugarStudioBot.bot.telegramBot;

import com.example.sugarStudioBot.bot.botService.SendBotMessageServiceImpl;
import com.example.sugarStudioBot.bot.command.CommandFull;
import com.example.sugarStudioBot.bot.configuration.InfoBotConfiguration;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import com.example.sugarStudioBot.service.service.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final InfoBotConfiguration config;
    private final CommandFull commandFull;
    private final UserRepository userRepository;


    public TelegramBot(InfoBotConfiguration config, UserRepository userRepository) {
        this.config = config;
        this.userRepository = userRepository;
        this.commandFull = new CommandFull(new SendBotMessageServiceImpl(this), userRepository);
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
                String text = update.getMessage().getText().trim();
                commandFull.findCommand(text).execute(update);
                log.info("Сообщение отправилось!");
            }
        } catch (Exception e) {
            log.error("Ошибка в методе onUpdateReceived: " + e.getMessage());
        }

    }
}

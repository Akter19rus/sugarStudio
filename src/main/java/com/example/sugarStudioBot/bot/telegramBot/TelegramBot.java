package com.example.sugarStudioBot.bot.telegramBot;

import com.example.sugarStudioBot.bot.botService.SendBotMessageServiceImpl;
import com.example.sugarStudioBot.bot.command.CommandFull;
import com.example.sugarStudioBot.bot.configuration.InfoBotConfiguration;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final InfoBotConfiguration config;
    private final CommandFull commandFull;
    private final UserRepository userRepository;
    private final InstallKeyboard installKeyboard;



    public TelegramBot(InfoBotConfiguration config, UserRepository userRepository, InstallKeyboard installKeyboard) {
        this.config = config;
        this.userRepository = userRepository;
        this.installKeyboard = installKeyboard;
        this.commandFull = new CommandFull(new SendBotMessageServiceImpl(this), userRepository, installKeyboard);
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
            if (update.hasMessage() && update.getMessage().hasText()) {
                log.info("Обработка текста!");
                String text = update.getMessage().getText().trim();
                commandFull.findCommand(text).execute(update);
                log.info("Сообщение отправилось!");
            } else if (update.hasCallbackQuery()) {
                log.info("Нажата кнопка!");
                String textButton = update.getCallbackQuery().getData();
                log.info("текст кнопки - " + textButton);
                commandFull.findCommand(textButton).execute(update);
            }
        } catch (Exception e) {
            log.error("Ошибка в методе onUpdateReceived: " + e.getMessage());
        }
    }
}

package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class StartCommand implements Command{

    private final SendBotMessageService sendMsg;
    private final UserRepository userRepository;
    public final static String START_MESSAGE = "Привет, я бот, и я помогаю вам узнать " +
            "все о депиляции и записаться на встречу с мастером";
    public final static String START_MSG_IF_KNOW = "Сново здравствуй, ты уже все знаешь - жми кнопку";


    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        if (userRepository.findUserByChatId(chatId) == null) {
            sendMsg.sendMessage(update.getMessage()
                    .getChatId()
                    .toString(), START_MESSAGE);
        } else sendMsg.sendMessage(update.getMessage()
                .getChatId()
                .toString(), START_MSG_IF_KNOW);
    }
}

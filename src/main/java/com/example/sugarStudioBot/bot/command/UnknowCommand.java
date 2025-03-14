package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class UnknowCommand implements Command{

    private final SendBotMessageService sendBotMsg;
    public static final String NO_MESSAGE = "Я не понял, либо пока еще не умею, давай заного";


    @Override
    public void execute(Update update) {
        sendBotMsg.sendMessage(update.getMessage()
                .getChatId()
                .toString(), NO_MESSAGE);
    }
}

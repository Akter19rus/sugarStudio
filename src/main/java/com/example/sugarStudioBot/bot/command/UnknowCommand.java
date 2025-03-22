package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.commandService.Command;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@AllArgsConstructor
public class UnknowCommand implements Command {

    private final SendBotMessageService sendBotMsg;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;

    public static final String NO_MESSAGE = "Я не понял, либо пока еще не умею, давай заного";

    @Override
    public void execute(Update update) {
        inlineKeyboardMarkup.setKeyboard(installKeyboard.backToMainMenu());
        sendBotMsg.sendMessage(update.getMessage()
                .getChatId()
                .toString(), NO_MESSAGE, inlineKeyboardMarkup);
    }
}

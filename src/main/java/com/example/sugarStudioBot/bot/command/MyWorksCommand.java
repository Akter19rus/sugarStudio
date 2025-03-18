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
public class MyWorksCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    public static final String MY_WORKS = "❤️Отправляем фото моих работ❤️";


    @Override
    public void execute(Update update) {
        log.info("Выполняется команда MyWorksCommand");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.backToMainMenu());
        sendBotMessageService.sendMessage(update.getCallbackQuery()
                .getMessage()
                .getChatId()
                .toString(), MY_WORKS, inlineKeyboardMarkup);
    }
}

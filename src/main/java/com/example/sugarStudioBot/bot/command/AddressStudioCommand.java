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
public class AddressStudioCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    public static final String ADDRESS = "❤️Фото расположения студии, текст адресс/как пройти❤️";


    @Override
    public void execute(Update update) {
        log.info("Выполняется команда AddressStudioCommand");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.backToMainMenu());
        sendBotMessageService.sendMessage(update.getCallbackQuery()
                .getMessage()
                .getChatId(), ADDRESS, inlineKeyboardMarkup);
    }
}

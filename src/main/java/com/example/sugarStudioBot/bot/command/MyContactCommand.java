package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.adminCommand.Command;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@AllArgsConstructor
public class MyContactCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    public static final String MY_CONTACT = "❤️Отправляем мои контакты, и ссылки на мессенджеры для связи❤️";


    @Override
    public void execute(Update update) {
        log.info("Выполняется команда MyContactCommand");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.backToMainMenu());
        sendBotMessageService.sendMessage(update.getCallbackQuery()
                .getMessage()
                .getChatId(), MY_CONTACT, inlineKeyboardMarkup);
    }
}

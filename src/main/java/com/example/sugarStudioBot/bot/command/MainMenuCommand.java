package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@AllArgsConstructor
public class MainMenuCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;

    public static final String MAIN_MENU = "❤\uFE0FВы в главном меню! - Нажмите нужную кнопку❤\uFE0F";

    @Override
    public void execute(Update update) {
        log.info("Выполняется команда главного меню");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.mainMenu());
        sendBotMessageService.sendMessage(update.getCallbackQuery()
                .getMessage()
                .getChatId()
                .toString(), MAIN_MENU, inlineKeyboardMarkup);
    }
}

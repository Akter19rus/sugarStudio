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
public class ReviewCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    public static final String REVIEW = "❤️Получаем список отзывов, создаем кнопку оставить отзыв❤️";


    @Override
    public void execute(Update update) {
        log.info("Выполняется команда ReviewCommand");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.review());
        sendBotMessageService.sendMessage(update.getCallbackQuery()
                .getMessage()
                .getChatId(), REVIEW, inlineKeyboardMarkup);
    }
}

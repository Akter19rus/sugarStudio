package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.dto.User;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.sugarStudioBot.bot.command.CommandName.*;

@AllArgsConstructor
@Slf4j
public class StartCommand implements Command {

    private final SendBotMessageService sendMsg;
    private final UserRepository userRepository;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;

    public final static String START_MESSAGE = "Привет, я бот, и я помогаю вам узнать " +
            "все о депиляции и записаться на встречу с мастером";
    public final static String START_MSG_IF_KNOW = "Сново здравствуй, ты уже все знаешь - жми кнопку";


    @Override
    public void execute(Update update) {
        log.info("Началось выполнения метода в startCommand");
        long chatId = update.getMessage().getChatId();
        String nickname = update.getMessage().getChat().getUserName();
        String name = update.getMessage().getChat().getFirstName();
        String surname = update.getMessage().getChat().getLastName();
        inlineKeyboardMarkup.setKeyboard(installKeyboard.mainMenu());

        if (userRepository.findUserByChatId(chatId) == null) {
            log.info("сохранение пользователя");
            userRepository.save(new User(null, chatId, nickname, name, surname));
            log.info("пользователь сохранен");

            sendMsg.sendMessage(update.getMessage()
                    .getChatId()
                    .toString(), START_MESSAGE, inlineKeyboardMarkup);
        } else {
            log.info("пользователь уже есть в БД");
            sendMsg.sendMessage(update.getMessage()
                    .getChatId()
                    .toString(), START_MSG_IF_KNOW, inlineKeyboardMarkup);
        }
    }
}

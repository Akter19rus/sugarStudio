package com.example.sugarStudioBot.bot.telegramBot;

import com.example.sugarStudioBot.bot.botService.SendBotMessageServiceImpl;
import com.example.sugarStudioBot.bot.command.CommandFull;
import com.example.sugarStudioBot.bot.configuration.InfoBotConfiguration;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final InfoBotConfiguration config;
    private final CommandFull commandFull;
    private final UserRepository userRepository;
    private final InstallKeyboard installKeyboard;
    private Map<Long, List<Integer>> messageIdsHistory = new ConcurrentHashMap<>();

    public TelegramBot(InfoBotConfiguration config, UserRepository userRepository
            , InstallKeyboard installKeyboard) {
        this.config = config;
        this.userRepository = userRepository;
        this.installKeyboard = installKeyboard;
        this.commandFull = new CommandFull(new SendBotMessageServiceImpl(this)
                , userRepository, installKeyboard);

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Начать диалог с ботом"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка списка команд бота - " + e.getMessage());
        }
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

                Long chatId = update.getMessage().getChatId();
                String text = update.getMessage().getText().trim();
                int messageIdToDelete = update.getMessage().getMessageId();
                deleteMessage(chatId, messageIdToDelete);

                List<Integer> ids = messageIdsHistory.get(chatId);
                log.info("ids: " + ids);
                if (ids != null && !ids.isEmpty()) {
                    int previousMessageId = ids.get(ids.size() - 1);
                    deleteMessage(chatId, previousMessageId);
                    log.info("Удалено предыдущее сообщение с Id: " + previousMessageId);
                }

                log.info("Обработка текста: " + text);
                commandFull.findCommand(text).execute(update);
            } else if (update.hasCallbackQuery()) {
                log.info("Нажата кнопка!");
                long chatIdCallBackQuery = update.getCallbackQuery().getMessage().getChatId();
                int messageIdCallBackQuery = update.getCallbackQuery().getMessage().getMessageId();
                String textButton = update.getCallbackQuery().getData();
                log.info("текст кнопки - " + textButton);

                deleteMessage(chatIdCallBackQuery, messageIdCallBackQuery);
                log.info("Сообщение после нажатия кнопки удалено с Id: " + messageIdCallBackQuery);

                commandFull.findCommand(textButton).execute(update);
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка в методе onUpdateReceived: " + e.getMessage());
        }
    }

    private void deleteMessage(long chatId, int messageId) throws TelegramApiException {
        if (chatId != 0 && messageId > 0) {
            try {
                DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), messageId);
                execute(deleteMessage);
                log.info("Сообщение с (id={}) удалено", messageId);
            } catch (TelegramApiException e) {
                log.error("Ошибка при удалении сообщения (id={}): {}", messageId, e.getMessage(), e);
            }
        }
    }

    private void updateMessageIds(Long chatId, int newMessageId) {
        messageIdsHistory.put(chatId, List.of(newMessageId));
    }

    public void saveBotMessageId(int messageId, long chatId) {
        updateMessageIds(chatId, messageId);
        log.info("Id сообщения бота сохранено: " + messageId);
    }
}

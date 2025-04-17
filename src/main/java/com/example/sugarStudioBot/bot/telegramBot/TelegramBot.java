package com.example.sugarStudioBot.bot.telegramBot;

import com.example.sugarStudioBot.bot.botService.SendBotMessageServiceImpl;
import com.example.sugarStudioBot.bot.botService.TelegramFileUploader;
import com.example.sugarStudioBot.bot.command.commandService.CommandFull;
import com.example.sugarStudioBot.bot.configuration.InfoBotConfiguration;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.repositories.ImageRepository;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import com.example.sugarStudioBot.service.service.images.ImageService;
import com.example.sugarStudioBot.service.service.review.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.sugarStudioBot.bot.command.commandService.CommandName.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final InfoBotConfiguration config;
    private final CommandFull commandFull;
    private final UserRepository userRepository;


    private final Map<Long, List<Integer>> messageIdsHistory = new ConcurrentHashMap<>();
    private final Map<Long, List<Integer>> photoMessageIdsHistory = new ConcurrentHashMap<>();
    private final Map<Long, String> userFlag = new ConcurrentHashMap<>();


    public TelegramBot(InfoBotConfiguration config, UserRepository userRepository
            , InstallKeyboard installKeyboard, ImageRepository imageRepository
            , TelegramFileUploader telegramFileUploader, ImageService imageService
            , ReviewService reviewService) {

        this.config = config;
        this.userRepository = userRepository;
        this.commandFull = new CommandFull(new SendBotMessageServiceImpl(this, telegramFileUploader, imageService)
                , userRepository, installKeyboard, imageRepository
                , reviewService);

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

    @Override
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
                deleteBotMessageId(chatId);

                if (userFlag.getOrDefault(chatId, "DEFAULT").equals("true")) {
                    log.info("состояние пользователя - ожидание отзыва");
                    commandFull.findCommand(LEAVE_REVIEW.getCommandName()).execute(update);
                    userFlag.remove(chatId);
                    log.info("флаг удален");
                } else {
                    log.info("Обработка текста: " + text);
                    if (userRepository.findUserByChatId(chatId) == null || !userRepository.findUserByChatId(chatId).isAdmin()) {
                        commandFull.findCommand(text).execute(update);
                    } else {
                        commandFull.findCommand(ADMIN_FORWARD_MESSAGE.getCommandName()).execute(update);
                    }
                }
                deleteMessage(chatId, messageIdToDelete);

            } else if (update.hasCallbackQuery()) {
                log.info("Нажата кнопка!");
                long chatIdCallBackQuery = update.getCallbackQuery().getMessage().getChatId();
                int messageIdCallBackQuery = update.getCallbackQuery().getMessage().getMessageId();
                String textButton = update.getCallbackQuery().getData();
                log.info("текст кнопки - " + textButton);

                if (textButton.equals(LEAVE_REVIEW.getCommandName())) {
                    log.info("добавилось состояние пользователя");
                    userFlag.put(chatIdCallBackQuery, "true");
                }
                if (textButton.equals(CONFIRM.getCommandName())) {
                    log.info("Подтверждение отправки поста, всем пользователям");
                    commandFull.findCommand(ADMIN_FORWARD_MESSAGE.getCommandName()).execute(update);
                }
                if (!textButton.equals(CONFIRM.getCommandName())) {
                    commandFull.findCommand(textButton).execute(update);
                }
                log.info("Сообщение после нажатия кнопки удалено с Id: " + messageIdCallBackQuery);
                deleteMessage(chatIdCallBackQuery, messageIdCallBackQuery);
                if (textButton.equals(MAIN_MENU.getCommandName())) {
                    deleteBotPhotoMessageId(chatIdCallBackQuery);
                }
            } else if (update.hasMessage() && userRepository.findUserByChatId(update.getMessage().getChatId()).isAdmin()) {
                commandFull.findCommand(ADMIN_FORWARD_MESSAGE.getCommandName()).execute(update);
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

    private void deleteBotMessageId(long chatId) throws TelegramApiException {
        List<Integer> ids = messageIdsHistory.get(chatId);
        log.info("ids: " + ids);
        if (ids != null && !ids.isEmpty()) {
            int previousMessageId = ids.get(ids.size() - 1);
            deleteMessage(chatId, previousMessageId);
            log.info("Удалено предыдущее сообщение с Id: " + previousMessageId);
        }
    }

    private void deleteBotPhotoMessageId(long chatId) throws TelegramApiException {
        List<Integer> ids = photoMessageIdsHistory.get(chatId);
        log.info("idsPhoto: " + ids);
        if (ids != null && !ids.isEmpty()) {
            for (int id : ids) {
                deleteMessage(chatId, id);
            }
            ids.clear();
        }
    }

    public void saveBotMessageId(int messageId, long chatId) {
        messageIdsHistory.put(chatId, List.of(messageId));
        log.info("Id сообщения бота сохранено: " + messageId);
    }

    public void saveBotPhotoMessageId(long chatId, List<Integer> messageIds) {
        for (int msgId : messageIds) {
            photoMessageIdsHistory.computeIfAbsent(chatId, k -> new ArrayList<>()).add(msgId);
            log.info("saveBotMessageId сохранил id: " + msgId);
        }
    }
}

package com.example.sugarStudioBot.bot.command.adminCommand;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.commandService.Command;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.model.User;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Slf4j
public class ForwardMessage implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserRepository userRepository;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;

    private static final Map<Long, Set<Integer>> messageIdsForMediaGroup = new ConcurrentHashMap<>();
    private static final String CONFIRMATION = "Подтвердите отправку сообщения всем пользователям бота!";
    private static Boolean isOneSendMessage = true;

    @Override
    public void execute(Update update) {
        log.info("Обработка сообщения от администратора началась!");
        if (!update.hasCallbackQuery() && update.getMessage().getMediaGroupId() == null) {
            log.info("не является медиагруппой");
            Message message = update.getMessage();
            List<User> allUser = userRepository.findAll();
            for (User user : allUser) {
                long chatIdUser = user.getChatId();
                sendBotMessageService.sendCopyMessageFromAdmin(chatIdUser, message);
            }
        } else if (!update.hasCallbackQuery()) {
            log.info("является медиагруппой");
            long chatId = update.getMessage().getChatId();
            Integer messageId = update.getMessage().getMessageId();

            messageIdsForMediaGroup.computeIfAbsent(chatId, k -> new LinkedHashSet<>()).add(messageId);
            log.info("idsList: " + messageIdsForMediaGroup);

            if (isOneSendMessage) {
                inlineKeyboardMarkup.setKeyboard(installKeyboard.confirm());
                sendBotMessageService.sendMessage(chatId, CONFIRMATION, inlineKeyboardMarkup);
                isOneSendMessage = false;
            }
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            Set<Integer> messageIds = messageIdsForMediaGroup.get(chatId);
            List<Integer> msgIds = new ArrayList<>(messageIds);
            List<User> allUser = userRepository.findAll();
            for (User user : allUser) {
                long chatIdUser = user.getChatId();
                log.info("разсылается медиагруппа");
                sendBotMessageService.sendCopyMediaGroupFromAdmin(chatIdUser, chatId, msgIds);
                isOneSendMessage = true;
            }
            messageIds.clear();
            msgIds.clear();
        }
    }
}
package com.example.sugarStudioBot.bot.botService;

import com.example.sugarStudioBot.bot.telegramBot.TelegramBot;
import com.example.sugarStudioBot.service.model.Images;
import com.example.sugarStudioBot.service.service.images.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.CopyMessages;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TelegramBot telegramBot;
    private final TelegramFileUploader telegramFileUploader;
    private final ImageService imageService;

    @Override
    public void sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            Message sentMessage = telegramBot.execute(sendMessage);
            int messageId = sentMessage.getMessageId();
            log.info("Сообщение отправлено c Id: " + messageId);
            executeMessageId(messageId, (chatId));
        } catch (TelegramApiException e) {
            log.error("сообщение не отправлено: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void sendCopyMessageFromAdmin(long chatId, Message message) {
        CopyMessage copyMessage = new CopyMessage();
        copyMessage.setChatId(chatId);
        copyMessage.setFromChatId(message.getChatId());
        copyMessage.setMessageId(message.getMessageId());
        copyMessage.enableHtml(true);
        try {
            telegramBot.execute(copyMessage);
            log.info("Сообщение от администратора отправилось");
        } catch (TelegramApiException e) {
            log.error("Сообщение от администратора НЕ отправилось! " + e.getMessage());
        }
    }

    @Override
    public void sendCopyMediaGroupFromAdmin(long chatId, long chatIdAdmin, List<Integer> messageIds) {
        CopyMessages copyMessages = new CopyMessages();
        copyMessages.setChatId(chatId);
        copyMessages.setFromChatId(chatIdAdmin);
        copyMessages.setMessageIds(messageIds);
        try {
            telegramBot.execute(copyMessages);
            log.info("Медиагруппа от администратора отправлена!");
        } catch (TelegramApiException e) {
            log.error("Медиагруппа от администратора НЕ отправилась!: " + e.getMessage());
        }
    }

    @Override
    public void sendMessagePhoto(long chatId, String message, List<Images> images, InlineKeyboardMarkup inlineKeyboardMarkup) {
        try {
            List<InputMediaPhoto> media = new ArrayList<>();
            List<Integer> messageIdsHistory = new ArrayList<>();
            for (Images img : images) {
                String fileId = img.getFileId();
                File file = new File(img.getFilePath());
                log.info("Найдены пути: " + file.getAbsolutePath());

                if (fileId != null && !fileId.isEmpty()) {
                    InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
                    inputMediaPhoto.setMedia(fileId);
                    media.add(inputMediaPhoto);
                } else if (file.exists() && file.isFile()) {
                    String addFileId = telegramFileUploader.uploadPhoto(chatId, file.getAbsolutePath(), telegramBot.getBotToken());
                    if (addFileId != null) {
                        imageService.uploadImageFileId(img.getName(), addFileId);
                    } else {
                        log.error("Ошибка загрузки файла: " + file.getAbsolutePath());
                    }
                } else {
                    log.error("Файл не найден: " + file.getAbsolutePath());
                }
            }
            if (!media.isEmpty()) {
                log.info("Началась отправка фото");
                SendMediaGroup msg = new SendMediaGroup();
                msg.setChatId(chatId);
                msg.setMedias(Collections.unmodifiableList(media));
                List<Message> messages = telegramBot.execute(msg);

                for (Message mes : messages) {
                    int messageId = mes.getMessageId();
                    messageIdsHistory.add(messageId);
                    log.info("сообщения потока с Id: " + messageId);
                }
                executeMessageIdForThread(chatId, messageIdsHistory);
            }
            sendMessage(chatId, message, inlineKeyboardMarkup);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки медиагруппы: " + e.getMessage());
        } catch (IOException e) {
            log.error("Ошибка загрузки файла: " + e.getMessage());
        }
    }

    private void executeMessageId(int messageId, long chatId) {
        telegramBot.saveBotMessageId(messageId, chatId);
    }

    private void executeMessageIdForThread(long chatId, List<Integer> messageIdsHistory) {
        telegramBot.saveBotPhotoMessageId(chatId, messageIdsHistory);
    }
}

package com.example.sugarStudioBot.bot.botService;

import com.example.sugarStudioBot.bot.telegramBot.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@AllArgsConstructor
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            telegramBot.execute(sendMessage);
            log.info("Сообщение отправлено");
        } catch (TelegramApiException e) {
            log.error("сообщение не отправлено: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

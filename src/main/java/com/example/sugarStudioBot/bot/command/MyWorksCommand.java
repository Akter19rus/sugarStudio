package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.adminCommand.Command;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.model.Images;
import com.example.sugarStudioBot.service.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
public class MyWorksCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    private final ImageRepository imageRepository;
    private static final String MY_WORKS = "❤️Отправляем фото моих работ❤️";
    private static final Pattern pattern = Pattern.compile("^works\\d$");


    @Override
    public void execute(Update update) {
        log.info("Выполняется команда MyWorksCommand");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.backToMainMenu());
        sendBotMessageService.sendMessagePhoto(update.getCallbackQuery()
                .getMessage()
                .getChatId(), MY_WORKS, findWorksImg(), inlineKeyboardMarkup);

    }

    private List<Images> findWorksImg() {
        List<Images> works = new ArrayList<>();
        List<Images> images = imageRepository.findAll();
        for (Images img : images) {
            String name = img.getName();
            if (name != null && pattern.matcher(name).matches()) {
                works.add(img);
                log.info("Найденные файлы с именем: " + img.getName());
            }
        }
        return works;
    }
}


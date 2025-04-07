package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.commandService.Command;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.model.Review;
import com.example.sugarStudioBot.service.service.review.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

@Slf4j
@AllArgsConstructor
public class ReviewCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    private final ReviewService reviewService;

    private final static String HEAD = "👇Показываю до 10-ти случайных отзывов👇\n\n";


    @Override
    public void execute(Update update) {
        log.info("Выполняется команда ReviewCommand");
        inlineKeyboardMarkup.setKeyboard(installKeyboard.review());
        sendBotMessageService.sendMessage(update.getCallbackQuery()
                        .getMessage()
                        .getChatId()
                , tenRandomReviews()
                , inlineKeyboardMarkup);
    }

    /**
     * Метод обрабатывает лист отзывов
     * Метод возвращает до 10-ти случайных и уникальных отзывов
     *
     * @return если лист пуст -> возвращает строку, что нет отзывов ->
     * возвращает обработаный список в формате toString
     * в шапку текста добавляется строка
     */
    private String tenRandomReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        int totalReview = reviews.size();

        if (totalReview == 0) {
            return "\uD83D\uDE12Отзывов пока нет, но вы можете возглавить список!\uD83D\uDE0E";
        }

        List<Review> randomReview = new ArrayList<>();
        Random random = new Random();
        int numReviewsToSelect = Math.min(10, totalReview);
        Set<Integer> selectedIndices = new HashSet<>();
        while (selectedIndices.size() < numReviewsToSelect) {
            selectedIndices.add(random.nextInt(totalReview));
        }

        for (int i : selectedIndices) {
            randomReview.add(reviews.get(i));
        }

        StringBuilder sb = new StringBuilder();
        for (Review review : randomReview) {
            sb.append(review);
        }
        return HEAD + sb;
    }
}

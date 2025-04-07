package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.commandService.Command;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.service.review.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@AllArgsConstructor
public class LeaveReviewCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final InstallKeyboard installKeyboard;
    private final ReviewService reviewService;

    public static final String LEAVE_REVIEW = " пожалуйста отправь мне отзыв!\uD83D\uDE0A";
    public static final String FINAL_LEAVE_REVIEW = "Спасибо за ваш отзыв!";
    private static boolean isLeaveReview;


    @Override
    public void execute(Update update) {
        inlineKeyboardMarkup.setKeyboard(installKeyboard.backToMainMenu());
        log.info("начался метод отзывов");
        if (!isLeaveReview) {
            log.info("Выполняется команда LeaveReviewCommand стартовое сообщение - флаг false!");
            String name = update.getCallbackQuery().getFrom().getFirstName();

            sendBotMessageService.sendMessage(update.getCallbackQuery()
                    .getMessage()
                    .getChatId(), name + " " + LEAVE_REVIEW, inlineKeyboardMarkup);
            isLeaveReview = true;
        } else {
            log.info("Выполняется команда LeaveReviewCommand сохранение отзыва - флаг true!");
            String review = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            reviewService.saveReviewForUser(chatId, review);
            sendBotMessageService.sendMessage(chatId, FINAL_LEAVE_REVIEW, inlineKeyboardMarkup);
            isLeaveReview = false;
        }
    }
}

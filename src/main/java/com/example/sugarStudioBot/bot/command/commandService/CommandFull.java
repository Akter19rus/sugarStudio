package com.example.sugarStudioBot.bot.command.commandService;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.command.*;
import com.example.sugarStudioBot.bot.command.adminCommand.ForwardMessage;
import com.example.sugarStudioBot.bot.keyboard.InstallKeyboard;
import com.example.sugarStudioBot.service.repositories.ImageRepository;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import com.example.sugarStudioBot.service.service.review.ReviewService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.example.sugarStudioBot.bot.command.commandService.CommandName.*;

@Component
@Slf4j
public class CommandFull {
    private final ImmutableMap<String, Command> commandMap;
    private final UnknowCommand unknowCommand;


    public CommandFull(SendBotMessageService sendMsg, UserRepository userRepository
            , InstallKeyboard installKeyboard, ImageRepository imageRepository
            , ReviewService reviewService) {
        this.commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendMsg
                        , userRepository
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(ABOUT_ME.getCommandName(), new AboutMeCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(MAIN_MENU.getCommandName(), new MainMenuCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(ADDRESS.getCommandName(), new AddressStudioCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(PRISE.getCommandName(), new PriseOrSaveCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(CONTACT.getCommandName(), new MyContactCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(REVIEW.getCommandName(), new ReviewCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard
                        , reviewService))
                .put(LEAVE_REVIEW.getCommandName(), new LeaveReviewCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard
                        , reviewService))
                .put(WORKS.getCommandName(), new MyWorksCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard
                        , imageRepository))
                .put(SIGN_UP.getCommandName(), new SignUpCommand(sendMsg
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .put(ADMIN_FORWARD_MESSAGE.getCommandName(), new ForwardMessage(sendMsg
                        , userRepository
                        , new InlineKeyboardMarkup()
                        , installKeyboard))
                .build();


        unknowCommand = new UnknowCommand(sendMsg
                , new InlineKeyboardMarkup()
                , installKeyboard);
    }


    public Command findCommand(String commandText) {
        log.info("метод поиска совпадений текст/команда - " + commandText);
        return commandMap.getOrDefault(commandText, unknowCommand);
    }
}

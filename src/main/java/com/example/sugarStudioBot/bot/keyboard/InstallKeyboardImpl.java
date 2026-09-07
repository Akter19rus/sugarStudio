package com.example.sugarStudioBot.bot.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.sugarStudioBot.bot.command.adminCommand.AdminCommandName.CONFIRM;
import static com.example.sugarStudioBot.bot.command.commandService.CommandName.*;
import static com.example.sugarStudioBot.bot.command.commandService.CommandName.WORKS;

@Service
public class InstallKeyboardImpl implements InstallKeyboard {

    public List<List<InlineKeyboardButton>> mainMenu() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(List.of(createButton(ABOUT_ME.getCommandName()), createButton(ADDRESS.getCommandName())));
        rowList.add(List.of(createButton(PRISE.getCommandName()), createButton(CONTACT.getCommandName())));
        rowList.add(List.of(createButton(REVIEW.getCommandName()), createButton(WORKS.getCommandName())));
        return rowList;
    }

    public List<List<InlineKeyboardButton>> backToMainMenu() {
        List<List<InlineKeyboardButton>> btn = new ArrayList<>();
        btn.add(List.of(createButton(MAIN_MENU.getCommandName())));
        return btn;
    }

    public List<List<InlineKeyboardButton>> review() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(List.of(createButton(LEAVE_REVIEW.getCommandName())));
        rowList.add(List.of(createButton(MAIN_MENU.getCommandName())));
        return rowList;
    }

    public List<List<InlineKeyboardButton>> signUp() {
        List<List<InlineKeyboardButton>> btn = new ArrayList<>();
        btn.add(List.of(createButton(SIGN_UP.getCommandName())));
        btn.add(List.of(createButton(MAIN_MENU.getCommandName())));
        return btn;
    }

    public List<List<InlineKeyboardButton>> confirm() {
        List<List<InlineKeyboardButton>> btn = new ArrayList<>();
        btn.add(List.of(createButton(CONFIRM.getAdminCommandName())));
        return btn;
    }

    public static InlineKeyboardButton createButton(String keyName) {
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText(keyName);
        btn.setCallbackData(keyName);
        return btn;
    }
}

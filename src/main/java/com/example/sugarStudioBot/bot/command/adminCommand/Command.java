package com.example.sugarStudioBot.bot.command.adminCommand;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    void execute(Update update);
}

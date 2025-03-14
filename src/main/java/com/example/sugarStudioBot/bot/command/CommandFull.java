package com.example.sugarStudioBot.bot.command;

import com.example.sugarStudioBot.bot.botService.SendBotMessageService;
import com.example.sugarStudioBot.bot.botService.SendBotMessageServiceImpl;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import com.example.sugarStudioBot.service.service.user.UserServiceImpl;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.example.sugarStudioBot.bot.command.CommandName.START;

@Component
@Slf4j
public class CommandFull {
    private final ImmutableMap<String, Command> commandMap;
    private final UnknowCommand unknowCommand;



    public CommandFull(SendBotMessageService sendMsg, UserRepository userRepository) {
        this.commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendMsg, userRepository)).build();

        unknowCommand = new UnknowCommand(sendMsg);
    }


    public Command findCommand(String commandText) {
        log.info("метод поиска совпадений текст/команда");
        return commandMap.getOrDefault(commandText, unknowCommand);
    }
}

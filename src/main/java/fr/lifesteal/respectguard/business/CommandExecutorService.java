package fr.lifesteal.respectguard.business;

import fr.lifesteal.respectguard.business.Interface.ICommandExecutorService;
import fr.lifesteal.respectguard.business.wrapper.Interface.ICommandDispatcherWrapper;

import java.util.List;

public class CommandExecutorService implements ICommandExecutorService {

    private final ICommandDispatcherWrapper commandDispatcher;

    public CommandExecutorService(ICommandDispatcherWrapper commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public void executeCommand(String command, String playerName) {
        command = command.replaceAll("%player%", playerName);
        this.commandDispatcher.dispatchConsoleCommand(command);
    }

    @Override
    public void executeCommands(List<String> commands, String playerName) {
        for (String command : commands) {
            this.executeCommand(command, playerName);
        }
    }
}

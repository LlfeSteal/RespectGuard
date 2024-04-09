package fr.lifesteal.respectguard.business;

import fr.lifesteal.respectguard.business.Interface.IChatGptService;
import fr.lifesteal.respectguard.business.Interface.IChatGuardService;
import fr.lifesteal.respectguard.business.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.wrapper.Interface.ICommandDispatcherWrapper;
import fr.lifesteal.respectguard.business.wrapper.Interface.IEventCallerWrapper;
import fr.lifesteal.respectguard.event.BadMessageEvent;
import org.bukkit.entity.Player;

public class ChatGuardService implements IChatGuardService {

    private final IChatGptService chatGptService;
    private final IConfigurationService configurationService;
    private final IEventCallerWrapper eventCaller;
    private final ICommandDispatcherWrapper commandDispatcher;

    public ChatGuardService(IChatGptService chatGptService, IConfigurationService configurationService, IEventCallerWrapper eventCaller, ICommandDispatcherWrapper commandDispatcher) {
        this.chatGptService = chatGptService;
        this.configurationService = configurationService;
        this.eventCaller = eventCaller;
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public boolean analyzePlayerMessage(Player player, String message) {
        boolean isBadMessage = this.chatGptService.IsBadMessage(message);

        if (!isBadMessage) return false;

        this.eventCaller.callEvent(new BadMessageEvent(player, message, true));

        for (String command : this.configurationService.getCommandsToExecute()) {
            this.commandDispatcher.dispatchConsoleCommand(command);
        }

        return this.configurationService.hasEventToBeCancel();
    }
}

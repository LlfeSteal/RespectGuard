package fr.lifesteal.respectguard.listener;

import fr.lifesteal.respectguard.business.config.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.wrapper.Interface.ICommandDispatcherWrapper;
import fr.lifesteal.respectguard.event.BadMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BadMessageListener implements Listener {

    private final IConfigurationService configurationService;
    private final ICommandDispatcherWrapper commandDispatcher;

    public BadMessageListener(IConfigurationService configurationService, ICommandDispatcherWrapper commandDispatcher) {
        this.configurationService = configurationService;
        this.commandDispatcher = commandDispatcher;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBadMessageEvent(BadMessageEvent event) {
        for (String command : this.configurationService.getCommandsToExecute()) {
            this.commandDispatcher.dispatchConsoleCommand(command);
        }
    }
}

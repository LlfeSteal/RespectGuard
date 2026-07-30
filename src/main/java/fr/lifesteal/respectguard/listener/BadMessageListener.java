package fr.lifesteal.respectguard.listener;

import fr.lifesteal.respectguard.business.Interface.ICommandExecutorService;
import fr.lifesteal.respectguard.business.config.Interface.IConfigurationService;
import fr.lifesteal.respectguard.event.BadMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BadMessageListener implements Listener {

    private final IConfigurationService configurationService;
    private final ICommandExecutorService commandExecutorService;

    public BadMessageListener(IConfigurationService configurationService, ICommandExecutorService commandExecutorService) {
        this.configurationService = configurationService;
        this.commandExecutorService = commandExecutorService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBadMessageEvent(BadMessageEvent event) {
        this.commandExecutorService.executeCommands(this.configurationService.getCommandsToExecute(), event.getPlayer().getName());
    }
}

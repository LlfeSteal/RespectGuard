package fr.lifesteal.respectguard.listener;

import fr.lifesteal.respectguard.event.BadMessageEvent;
import fr.lifesteal.respectguard.service.Interface.IChatGptService;
import fr.lifesteal.respectguard.service.Interface.IConfigurationService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatListener implements Listener {

    private final JavaPlugin plugin;
    private final IChatGptService chatGptService;
    private final IConfigurationService configurationService;

    public ChatListener(JavaPlugin plugin, IChatGptService chatGptService,IConfigurationService configurationService) {
        this.plugin = plugin;
        this.chatGptService = chatGptService;
        this.configurationService = configurationService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        String message = event.getMessage();

        boolean isBadMessage = this.chatGptService.IsBadMessage(message);

        player.sendMessage("Le message contient %message une insulte ? %response"
                .replace("%message", message)
                .replace("%response", Boolean.toString(isBadMessage)));


        if (isBadMessage) {
            event.setCancelled(this.configurationService.hasEventToBeCancel());
            this.plugin.getServer().getPluginManager().callEvent(new BadMessageEvent(player, message, true));

            for (String command : this.configurationService.getCommandsToExecute()) {
                this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), command);
            }
        }
    }

    @EventHandler
    public void onBadMessage(BadMessageEvent event) {
        event.getPlayer().sendMessage("BAD MESSAGE : " + event.getMessage());
    }
}

package fr.lifesteal.respectguard.listener;

import fr.lifesteal.respectguard.business.Interface.IChatGuardService;
import fr.lifesteal.respectguard.event.BadMessageEvent;
import fr.lifesteal.respectguard.business.Interface.IChatGptService;
import fr.lifesteal.respectguard.business.Interface.IConfigurationService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatListener implements Listener {

    private final IChatGuardService chatGuardService;

    public ChatListener(IChatGuardService chatGuardService) {
        this.chatGuardService = chatGuardService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        String message = event.getMessage();

        event.setCancelled(this.chatGuardService.analyzePlayerMessage(player, message));
    }

    @EventHandler
    public void onBadMessage(BadMessageEvent event) {
        event.getPlayer().sendMessage("BAD MESSAGE : " + event.getMessage());
    }
}

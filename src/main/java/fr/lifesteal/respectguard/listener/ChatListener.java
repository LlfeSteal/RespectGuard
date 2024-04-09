package fr.lifesteal.respectguard.listener;

import fr.lifesteal.respectguard.business.Interface.IChatGuardService;
import fr.lifesteal.respectguard.event.BadMessageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

        boolean hasEventToBeCancelled = this.chatGuardService.analyzePlayerMessage(player, message);
        event.setCancelled(hasEventToBeCancelled);
    }

    @EventHandler
    public void onBadMessage(BadMessageEvent event) {
        event.getPlayer().sendMessage("BAD MESSAGE : " + event.getMessage());
    }
}

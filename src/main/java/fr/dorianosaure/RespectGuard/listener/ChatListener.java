package fr.dorianosaure.RespectGuard.listener;

import fr.dorianosaure.RespectGuard.service.Interface.IChatGptService;
import fr.dorianosaure.RespectGuard.event.BadMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final IChatGptService chatGptService;

    public ChatListener(IChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        String message = event.getMessage();

        boolean isBadMessage = this.chatGptService.IsBadMessage(message);

        if (isBadMessage) {
            event.setCancelled(true);
            Bukkit.getPluginManager().callEvent(new BadMessageEvent(event.getPlayer(), message));
        }
    }
}

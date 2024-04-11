package fr.lifesteal.respectguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class BadMessageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String message;
    private final Set<String> harmfulCategories;

    public BadMessageEvent(Player player, String message, boolean isAsync, Set<String> harmfulCategories) {
        super(isAsync);
        this.player = player;
        this.message = message;
        this.harmfulCategories = harmfulCategories;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public Set<String> getHarmfulCategories() {
        return harmfulCategories;
    }
}

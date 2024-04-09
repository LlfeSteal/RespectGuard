package fr.lifesteal.respectguard.business.wrapper;

import fr.lifesteal.respectguard.business.wrapper.Interface.IEventCallerWrapper;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class EventCallerWrapper implements IEventCallerWrapper {

    private final JavaPlugin plugin;

    public EventCallerWrapper(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void callEvent(Event event) {
        this.plugin.getServer().getPluginManager().callEvent(event);
    }
}

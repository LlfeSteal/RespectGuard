package fr.lifesteal.respectguard.business.wrapper;

import fr.lifesteal.respectguard.business.wrapper.Interface.ICommandDispatcherWrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandDispatcherWrapper implements ICommandDispatcherWrapper {

    private final JavaPlugin plugin;

    public CommandDispatcherWrapper(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void dispatchConsoleCommand(String command) {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), command);
        });
    }
}

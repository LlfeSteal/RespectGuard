package fr.dorianosaure.RespectGuard;

import fr.dorianosaure.RespectGuard.listener.ChatListener;
import fr.dorianosaure.RespectGuard.service.ChatGptService;
import fr.dorianosaure.RespectGuard.service.ConfigurationService;
import fr.dorianosaure.RespectGuard.service.Interface.IChatGptService;
import fr.dorianosaure.RespectGuard.service.Interface.ILoggerService;
import fr.dorianosaure.RespectGuard.service.LoggerService;
import fr.dorianosaure.RespectGuard.service.Interface.IConfigurationService;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RespectGuard extends JavaPlugin  {

    private IConfigurationService configurationService;
    private IChatGptService chatGptService;
    private ILoggerService loggerService;

    @Override
    public void onEnable() {
        this.initServices();
        this.initListeners();
    }

    private void initListeners() {
        this.registerListener(new ChatListener(this.chatGptService));
    }

    private void initServices() {
        Logger pluginLogger = PluginLogger.getLogger(RespectGuard.class.toString());
        this.loggerService = new LoggerService(pluginLogger);
        this.configurationService = new ConfigurationService(this, this.getConfig());
        this.chatGptService = new ChatGptService(this.loggerService, this.configurationService);
    }

    private void initConfiguration() {
        this.configurationService.initDefaultConfiguration();
    }

    private void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {

    }
}
package fr.lifesteal.respectguard;

import fr.lifesteal.respectguard.listener.ChatListener;
import fr.lifesteal.respectguard.service.ChatGptService;
import fr.lifesteal.respectguard.service.CacheService;
import fr.lifesteal.respectguard.service.ConfigurationService;
import fr.lifesteal.respectguard.service.Interface.IChatGptService;
import fr.lifesteal.respectguard.service.Interface.ICacheService;
import fr.lifesteal.respectguard.service.Interface.ILoggerService;
import fr.lifesteal.respectguard.service.LoggerService;
import fr.lifesteal.respectguard.service.Interface.IConfigurationService;
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
        this.initConfiguration();
        this.initListeners();
    }

    private void initListeners() {
        this.registerListener(new ChatListener(this, this.chatGptService, this.configurationService));
    }

    private void initServices() {
        Logger pluginLogger = PluginLogger.getLogger(RespectGuard.class.toString());
        this.loggerService = new LoggerService(pluginLogger);
        ICacheService configurationCacheService = new CacheService();
        this.configurationService = new ConfigurationService(this, this.getConfig(), configurationCacheService);
        this.chatGptService = new ChatGptService(this.loggerService, this.configurationService);
    }

    private void initConfiguration() {
        this.configurationService.initDefaultConfiguration();
        this.configurationService.loadConfiguration();
    }

    private void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {

    }
}
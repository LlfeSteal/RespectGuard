package fr.lifesteal.respectguard;

import fr.lifesteal.respectguard.business.*;
import fr.lifesteal.respectguard.business.Interface.IChatGuardService;
import fr.lifesteal.respectguard.business.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.wrapper.CommandDispatcherWrapper;
import fr.lifesteal.respectguard.business.wrapper.EventCallerWrapper;
import fr.lifesteal.respectguard.listener.ChatListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class RespectGuard extends JavaPlugin  {
    private IConfigurationService configurationService;
    private IChatGuardService chatGuardService;

    @Override
    public void onEnable() {
        this.initServices();
        this.initConfiguration();
        this.initListeners();
    }

    private void initListeners() {
        this.registerListener(new ChatListener(this.chatGuardService));
    }

    private void initServices() {
        var pluginLogger = PluginLogger.getLogger(RespectGuard.class.toString());
        var loggerService = new LoggerService(pluginLogger);
        var configurationCacheService = new CacheService();
        this.configurationService = new ConfigurationService(this, this.getConfig(), configurationCacheService);

        var httpRequestService = new HttpRequestService(loggerService);
        var chatGptService = new ChatGptService(loggerService, this.configurationService, httpRequestService);

        var commandDispatcher = new CommandDispatcherWrapper(this);
        var eventCaller = new EventCallerWrapper(this);
        this.chatGuardService = new ChatGuardService(chatGptService, this.configurationService, eventCaller, commandDispatcher);
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
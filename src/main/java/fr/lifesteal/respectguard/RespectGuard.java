package fr.lifesteal.respectguard;

import fr.lifesteal.respectguard.business.*;
import fr.lifesteal.respectguard.business.Interface.*;
import fr.lifesteal.respectguard.business.wrapper.CommandDispatcherWrapper;
import fr.lifesteal.respectguard.business.wrapper.EventCallerWrapper;
import fr.lifesteal.respectguard.business.wrapper.Interface.ICommandDispatcherWrapper;
import fr.lifesteal.respectguard.business.wrapper.Interface.IEventCallerWrapper;
import fr.lifesteal.respectguard.listener.ChatListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

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
        Logger pluginLogger = PluginLogger.getLogger(RespectGuard.class.toString());
        ILoggerService loggerService = new LoggerService(pluginLogger);
        ICacheService configurationCacheService = new CacheService();
        this.configurationService = new ConfigurationService(this, this.getConfig(), configurationCacheService);
        IChatGptService chatGptService = new ChatGptService(loggerService, this.configurationService);

        ICommandDispatcherWrapper commandDispatcher = new CommandDispatcherWrapper(this);
        IEventCallerWrapper eventCaller = new EventCallerWrapper(this);
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
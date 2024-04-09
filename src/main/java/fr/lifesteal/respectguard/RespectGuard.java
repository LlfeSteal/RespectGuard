package fr.lifesteal.respectguard;

import fr.lifesteal.pluginframework.api.config.ConfigService;
import fr.lifesteal.pluginframework.core.command.PluginCommand;
import fr.lifesteal.pluginframework.core.plugin.PluginBase;
import fr.lifesteal.respectguard.business.ChatGptService;
import fr.lifesteal.respectguard.business.ChatGuardService;
import fr.lifesteal.respectguard.business.HttpRequestService;
import fr.lifesteal.respectguard.business.Interface.IChatGuardService;
import fr.lifesteal.respectguard.business.LoggerService;
import fr.lifesteal.respectguard.business.config.ConfigurationService;
import fr.lifesteal.respectguard.business.wrapper.CommandDispatcherWrapper;
import fr.lifesteal.respectguard.business.wrapper.EventCallerWrapper;
import fr.lifesteal.respectguard.listener.ChatListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;

import java.util.ArrayList;
import java.util.List;

public class RespectGuard extends PluginBase {
    private ConfigurationService configurationService;
    private IChatGuardService chatGuardService;

    @Override
    public void init() {
        var pluginLogger = PluginLogger.getLogger(RespectGuard.class.toString());
        var loggerService = new LoggerService(pluginLogger);

        var configurationRepository = getConfigRepositoryFactory().getNewYamlRepository("", "config.yml");
        this.configurationService = new ConfigurationService(getLogger(), configurationRepository);

        var httpRequestService = new HttpRequestService(loggerService);
        var chatGptService = new ChatGptService(loggerService, this.configurationService, httpRequestService);

        var commandDispatcher = new CommandDispatcherWrapper(this);
        var eventCaller = new EventCallerWrapper(this);
        this.chatGuardService = new ChatGuardService(chatGptService, this.configurationService, eventCaller, commandDispatcher);
    }

    @Override
    protected List<ConfigService> registerConfigurationsServices() {
        return new ArrayList<>() {{
            add(configurationService);
        }};
    }

    @Override
    protected List<Listener> registerListeners() {
        return new ArrayList<>(){{
            add(new ChatListener(chatGuardService));
        }};
    }

    @Override
    protected List<PluginCommand> registerCommands() {
        return new ArrayList<>();
    }
}
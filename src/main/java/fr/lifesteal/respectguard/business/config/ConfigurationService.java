package fr.lifesteal.respectguard.business.config;

import fr.lifesteal.pluginframework.api.config.ConfigRepository;
import fr.lifesteal.pluginframework.core.config.ConfigParam;
import fr.lifesteal.pluginframework.core.config.ConfigServiceBase;
import fr.lifesteal.respectguard.business.config.Interface.IConfigurationService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * {@inheritDoc}
 */
public class ConfigurationService extends ConfigServiceBase implements IConfigurationService {

    @ConfigParam(paramKey = "chatGPT.api-key")
    private String chatGptApiKey = "";

    @ConfigParam(paramKey = "chatGPT.model")
    private String chatGptModel = "gpt-3.5-turbo";

    @ConfigParam(paramKey = "event.cancel-event")
    private boolean hasToCancelEvent = false;

    @ConfigParam(paramKey = "event.commands")
    private List<String> eventCommands = new ArrayList<>();

    /**
     * Initialise une instance de la class ConfigurationService
     * @param logger Service de gestion des logs.
     * @param configRepository Service de gestion des données.
     */
    public ConfigurationService(Logger logger, ConfigRepository configRepository) {
        super(logger, configRepository);
    }

    @Override
    public String getChatGptApiKey() {
        return chatGptApiKey;
    }

    @Override
    public String getChatGptModel() {
        return chatGptModel;
    }

    @Override
    public boolean hasEventToBeCancel() {
        return hasToCancelEvent;
    }

    @Override
    public List<String> getCommandsToExecute() {
        return eventCommands;
    }
}

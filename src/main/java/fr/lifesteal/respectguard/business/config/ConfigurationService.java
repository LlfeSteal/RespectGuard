package fr.lifesteal.respectguard.business.config;

import fr.lifesteal.respectguard.constante.ConfigurationConstante;
import fr.lifesteal.respectguard.business.config.Interface.ICacheService;
import fr.lifesteal.respectguard.business.config.Interface.IConfigurationService;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class ConfigurationService implements IConfigurationService {

    private final JavaPlugin plugin;
    private final FileConfiguration fileConfiguration;
    private final ICacheService cacheService;

    /**
     * Initialise une instance de la class ConfigurationService
     * @param plugin Plugin propriétaire du fichier de configuration.
     * @param fileConfiguration Fichier de configuration cible.
     * @param cacheService Service de gestion de cache.
     */
    public ConfigurationService(JavaPlugin plugin, FileConfiguration fileConfiguration, ICacheService cacheService) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;
        this.cacheService = cacheService;
    }

    /**
     * {@inheritDoc}
     */
    public void initDefaultConfiguration() {
        this.fileConfiguration.addDefault(ConfigurationConstante.CHATGPT_API_KEY, ConfigurationConstante.CHATGPT_API_KEY_DEFAULT_VALUE);
        this.fileConfiguration.addDefault(ConfigurationConstante.CHATGPT_MODEL_KEY, ConfigurationConstante.CHATGPT_MODEL_KEY_DEFAULT_VALUE);
        this.fileConfiguration.addDefault(ConfigurationConstante.EVENT_CANCEL_KEY, ConfigurationConstante.EVENT_CANCEL_KEY_DEFAULT_VALUE);
        this.fileConfiguration.addDefault(ConfigurationConstante.EVENT_COMMANDS_KEY, ConfigurationConstante.EVENT_COMMANDS_KEY_DEFAULT_VALUE);

        this.fileConfiguration.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

    /**
     * {@inheritDoc}
     */
    public void loadConfiguration() {
        this.loadStringValue(ConfigurationConstante.CHATGPT_API_KEY);
        this.loadStringValue(ConfigurationConstante.CHATGPT_MODEL_KEY);
        this.loadBooleanValue(ConfigurationConstante.EVENT_CANCEL_KEY);
        this.loadStringListValue(ConfigurationConstante.EVENT_COMMANDS_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getChatGptApiKey() {
        return (String) this.cacheService.getValue(ConfigurationConstante.CHATGPT_API_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getChatGptModel() {
        return (String) this.cacheService.getValue(ConfigurationConstante.CHATGPT_MODEL_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasEventToBeCancel() {
        return (boolean) this.cacheService.getValue(ConfigurationConstante.EVENT_CANCEL_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCommandsToExecute() {
        return (List<String>) this.cacheService.getValue(ConfigurationConstante.EVENT_COMMANDS_KEY);
    }

    private void loadStringValue(String configurationKey) {
        this.cacheService.setValue(configurationKey, this.fileConfiguration.getString(configurationKey));
    }

    private void loadBooleanValue(String configurationKey) {
        this.cacheService.setValue(configurationKey, this.fileConfiguration.getBoolean(configurationKey));
    }

    private void loadStringListValue(String configurationKey) {
        this.cacheService.setValue(configurationKey, this.fileConfiguration.getStringList(configurationKey));
    }

}

package fr.dorianosaure.RespectGuard.service;

import fr.dorianosaure.RespectGuard.RespectGuard;
import fr.dorianosaure.RespectGuard.constante.ConfigurationConstante;
import fr.dorianosaure.RespectGuard.service.Interface.IConfigurationService;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationService implements IConfigurationService {

    private final RespectGuard respectGuard;
    private final FileConfiguration fileConfiguration;
    private final Map<String, String> configurationValues = new HashMap<>();

    public ConfigurationService(RespectGuard respectGuard, FileConfiguration fileConfiguration) {
        this.respectGuard = respectGuard;
        this.fileConfiguration = fileConfiguration;
    }

    public void initDefaultConfiguration() {
        this.fileConfiguration.addDefault(ConfigurationConstante.CHATGPT_API_KEY, ConfigurationConstante.CHATGPT_API_KEY_DEFAULT_VALUE);
        this.fileConfiguration.addDefault(ConfigurationConstante.CHATGPT_MODEL_KEY, ConfigurationConstante.CHATGPT_MODEL_KEY_DEFAULT_VALUE);

        this.fileConfiguration.options().copyDefaults(true);
        this.respectGuard.saveConfig();
    }

    public void loadConfiguration() {
        this.loadValueConfiguration(ConfigurationConstante.CHATGPT_API_KEY);
        this.loadValueConfiguration(ConfigurationConstante.CHATGPT_MODEL_KEY);
    }

    @Override
    public String getChatGptApiKey() {
        return configurationValues.get(ConfigurationConstante.CHATGPT_API_KEY);
    }

    @Override
    public String getChatGptModel() {
        return configurationValues.get(ConfigurationConstante.CHATGPT_MODEL_KEY);
    }

    private void loadValueConfiguration(String configurationKey) {
        configurationValues.put(configurationKey, this.fileConfiguration.getString(configurationKey));
    }
}

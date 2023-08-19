package fr.dorianosaure.RespectGuard.service.Interface;

public interface IConfigurationService {
    void initDefaultConfiguration();
    String getChatGptApiKey();
    String getChatGptModel();
}

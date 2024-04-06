package fr.lifesteal.respectguard.service.Interface;

import java.util.List;

/**
 * Service de gestion de la configuration.
 */
public interface IConfigurationService {

    /**
     * Méthode permettant d'initialiser le fichier de configuration.
     */
    void initDefaultConfiguration();

    /**
     * Méthode permettant de charger la configuration en cache depuis le fichier de configuration.
     */
    void loadConfiguration();

    /**
     * Méthode permettant de récupérer la clé de l'API ChatGPT.
     * @return Retourne la clé de l'API ChatGPT.
     */
    String getChatGptApiKey();

    /**
     * Méthode permettant de récupérer le model ChatGPT à utiliser.
     * @return Retourne le nom du model ChatGPT à utiliser.
     */
    String getChatGptModel();

    /**
     * Méthode permettant de récupérer une valeur indiquant si l'event de chat doit être annulé en cas d'insulte dans le message.
     * @return Retourne une valeur indiquant si l'event de chat doit être annulé en cas d'insulte dans le message.
     */
    boolean hasEventToBeCancel();

    /**
     * Méthode permettant de récupérer les commandes à exécuter en cas d'insulte dans le message.
     * @return Retourne la liste des commandes à exécuter en cas d'insulte dans le message.
     */
    List<String> getCommandsToExecute();
}

package fr.lifesteal.respectguard.business.Interface;

/**
 * Service de gestion des logs.
 */
public interface ILoggerService {

    /**
     * Méthode permettant de logger un message et une exception en erreur.
     * @param message Message à logger.
     * @param exception Exception à logger.
     */
    void Error(String message, Exception exception);

    /**
     * Méthode permettant de logger un message en debug.
     * @param message Message à logger.
     */
    void Debug(String message);
}

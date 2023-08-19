package fr.dorianosaure.RespectGuard.service.Interface;

/**
 * Service de gestion des logs.
 */
public interface ILoggerService {
    /**
     * Méthode permettant de logger un message et une exception en erreur.
     * @param string Message à logger.
     * @param exception Exception à logger.
     */
    void Error(String string, Exception exception);
}

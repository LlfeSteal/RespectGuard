package fr.lifesteal.respectguard.business.wrapper.Interface;

/**
 * Service d'exécution de commande sur le serveur.
 */
public interface ICommandDispatcherWrapper {

    /**
     * Méthode permettant de d'exécuter une commande.
     * @param command commande à exécuter.
     */
    void dispatchConsoleCommand(String command);
}


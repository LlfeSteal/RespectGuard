package fr.lifesteal.respectguard.business.Interface;

import java.util.List;

public interface ICommandExecutorService {

    /**
     * Méthode permettant d'exécuter une commande.
     * @param command Commande à exécuter.
     * @param playerName Nom du joueur.
     */
    void executeCommand(String command, String playerName);

    /**
     * Méthode permettant d'exécuter plusieurs commandes.
     * @param commands Commandes à exécuter.
     * @param playerName Nom du joueur.
     */
    void executeCommands(List<String> commands, String playerName);
}

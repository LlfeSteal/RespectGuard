package fr.dorianosaure.RespectGuard.service.Interface;

/**
 * Service d'intéraction avec l'API ChatGPT.
 */
public interface IChatGptService {

    /**
     * Méthode permettant de vérifier si un message contient une insulte.
     * @param message Message à tester.
     * @return Retourne vrai si le message contient une insulte, faux sinon.
     */
    boolean IsBadMessage(String message);
}

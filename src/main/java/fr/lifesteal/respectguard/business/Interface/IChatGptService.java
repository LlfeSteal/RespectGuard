package fr.lifesteal.respectguard.business.Interface;

import fr.lifesteal.respectguard.business.object.MessageAnalysesResult;

/**
 * Service d'intéraction avec l'API ChatGPT.
 */
public interface IChatGptService {

    /**
     * Méthode permettant de vérifier si un message contient une insulte.
     * @param message Message à tester.
     * @return Retourne vrai si le message contient une insulte, faux sinon.
     */
    MessageAnalysesResult analyzeMessage(String message);
}

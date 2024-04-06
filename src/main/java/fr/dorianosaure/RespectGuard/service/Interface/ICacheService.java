package fr.dorianosaure.RespectGuard.service.Interface;

/**
 * Service de gestion de cache clé/valeur.
 */
public interface ICacheService {

    /**
     * Méthode permettant de stocker un objet dans le cache.
     * Remarque: La clef doit être unique.
     * @param key Clé de l'objet dans le cache.
     * @param value Valeur à stocker dans le cache.
     */
    void setValue(String key, Object value);

    /**
     * Méthode permettant de récupérer l'objet dans le cache à partir de sa clé.
     * @param key Clé de l'objet dans le cache.
     * @return Retourne la valeur stocké pour la clé donnée si elle est présente, null sinon.
     */
    Object getValue(String key);
}

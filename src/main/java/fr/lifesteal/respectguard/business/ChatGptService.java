package fr.lifesteal.respectguard.business;

import fr.lifesteal.respectguard.business.Interface.IChatGptService;
import fr.lifesteal.respectguard.business.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.Interface.ILoggerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * {@inheritDoc}
 * TODO: Extraire le méchanisme de requete HTTP dans une autre classe.
 */
public class ChatGptService implements IChatGptService {
    private static final String CHATGPT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String IS_BAD_MESSAGE_REQUEST = "Uniquement par oui ou par non, est-ce que cette phrase contient une insulte : %testedMessage";
    private final IConfigurationService configurationService;
    private final ILoggerService loggerService;

    /**
     * Initialise une instance de la class ChatGptService
     * @param loggerService service de gestion des logs.
     * @param configurationService service de gestion de la configuration.
     */
    public ChatGptService(ILoggerService loggerService, IConfigurationService configurationService) {
        this.loggerService = loggerService;
        this.configurationService = configurationService;
    }

    /**
     * {@inheritDoc}
     */
    public boolean IsBadMessage(String message) {
        String requestMessage = IS_BAD_MESSAGE_REQUEST
                .replace("%testedMessage", message);

        this.loggerService.Debug("RequestMessage = %message".replace("%message", requestMessage));

        String response = GetChatGptResponse(requestMessage);

        this.loggerService.Debug("RequestReponse = %message".replace("%message", response));

        return response.equalsIgnoreCase("Oui.");
    }

    /**
     * Méthode permettant d'envoyer une requete à l'API ChatGpt et de récupérer la réponse.
     * @param requestMessage Message à envoyer à ChatGpt.
     * @return Retourne la réponse de ChatGpt ou vide si une erreur s'est produite.
     */
    private String GetChatGptResponse(String requestMessage) {
        try {
            HttpURLConnection connection  = GetConnection();

            this.sendRequest(connection, requestMessage);
            String response = this.GetRequestResult(connection);

            this.loggerService.Debug("RawRequestReponse = %message".replace("%message", response));

            return extractContentFromResponse(response);
        }
        catch (Exception ex) {
            loggerService.Error("Erreur lors de la récupération de la réponse de ChatGpt :" + ex.getMessage(), ex);
            return "";
        }
    }

    /**
     * Méthode permettant d'initialiser la requete HTTP.
     * @return Retourne la connection HTTP créée.
     * @throws IOException Léve une exception si une erreur I/O se produit sur le connection.
     */
    private HttpURLConnection GetConnection() throws IOException {
        URL url = new URL(CHATGPT_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + this.configurationService.getChatGptApiKey());

        this.loggerService.Debug("Authorization = %Authorization".replace("%Authorization", "Bearer " + this.configurationService.getChatGptApiKey()));
        return connection;
    }

    /**
     * Méthode permettant de renseigner les données et d'envoyer la requete à l'API ChatGpt.
     * @param connection Connection à utiliser pour écrire les données.
     * @param requestMessage Données de la requête à envoyer.
     * @throws IOException Léve une exception si une erreur I/O se produit sur le connection.
     */
    private void sendRequest(HttpURLConnection connection, String requestMessage) throws IOException {
        String prompt = "[{\"role\": \"user\", \"content\": \"" + requestMessage + "\"}]";
        String requestBody = "{\"model\": \"" + this.configurationService.getChatGptModel()  + "\", \"messages\": " + prompt + "}";

        this.loggerService.Debug("requestBody = %message".replace("%message", requestBody));

        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestBody);
        writer.flush();
        writer.close();
    }

    /**
     * Méthode permettant de récupérer le resultat de la requête HTTP.
     * @param connection Connection à utiliser pour récupérer les resultats.
     * @return Retourne le resultat sous la forme d'une chaine json.
     * @throws IOException Léve une exception si une erreur I/O se produit sur le connection.
     */
    private String GetRequestResult(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        return response.toString();
    }


    /**
     * Méthode permettant d'éxtraire la réponse de chatGpt depuis les réponses de la requete.
     * @param response Reponse de la requete à ChatGpt.
     * @return Retourne la phrase de reponse de ChatGpt.
     */
    private static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}

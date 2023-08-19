package fr.dorianosaure.RespectGuard.service;

import fr.dorianosaure.RespectGuard.service.Interface.IChatGptService;
import fr.dorianosaure.RespectGuard.service.Interface.IConfigurationService;
import fr.dorianosaure.RespectGuard.service.Interface.ILoggerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @inheritDoc
 */
public class ChatGptService implements IChatGptService {
    private static final String CHATGPT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String IS_BAD_MESSAGE_REQUEST = "Salut, peux-tu répondre uniquement par oui ou par non, Est-ce que cette phrase contient oui ou non une insulte : %testedMessage";

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
     * @inheritDoc
     */
    public boolean IsBadMessage(String message) {
        String requestMessage = IS_BAD_MESSAGE_REQUEST
                .replace("%testedMessage", message);
        String response = GetChatGptResponse(requestMessage);

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

            return extractContentFromResponse(response);
        }
        catch (Exception ex) {
            loggerService.Error("Erreur lors de la récupération de la réponse de ChatGpt.", ex);
            return "";
        }
    }

    private void sendRequest(HttpURLConnection connection, String requestMessage) throws IOException {
        String requestBody = "{\"model\": \"%model\", \"message\": [{\"role\": \"user\", \"content\": \"%message \"}]}}"
                .replace("%model", this.configurationService.getChatGptModel())
                .replace("%message", requestMessage);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestBody);
        writer.flush();
        writer.close();
    }

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

    private HttpURLConnection GetConnection() throws IOException {
        URL url = new URL(CHATGPT_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer" + this.configurationService.getChatGptApiKey());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        return connection;
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

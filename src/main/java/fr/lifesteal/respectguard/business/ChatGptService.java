package fr.lifesteal.respectguard.business;

import fr.lifesteal.respectguard.business.Interface.IChatGptService;
import fr.lifesteal.respectguard.business.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.Interface.IHttpRequestService;
import fr.lifesteal.respectguard.business.Interface.ILoggerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * {@inheritDoc}
 */
public class ChatGptService implements IChatGptService {
    private static final String CHATGPT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String IS_BAD_MESSAGE_REQUEST = "Uniquement par oui ou par non, est-ce que cette phrase contient une insulte : %testedMessage";
    private final IConfigurationService configurationService;
    private final IHttpRequestService httpRequestService;
    private final ILoggerService loggerService;

    /**
     * Initialise une instance de la class ChatGptService
     * @param loggerService service de gestion des logs.
     * @param configurationService service de gestion de la configuration.
     */
    public ChatGptService(ILoggerService loggerService, IConfigurationService configurationService, IHttpRequestService httpRequestService) {
        this.loggerService = loggerService;
        this.configurationService = configurationService;
        this.httpRequestService = httpRequestService;
    }

    /**
     * {@inheritDoc}
     */
    public boolean IsBadMessage(String message) {
        this.loggerService.Debug("RequestMessage = %message".replace("%message", message));

        String response = this.httpRequestService.getRequestResponse(
                CHATGPT_URL,
                "POST",
                getRequestPrompt(message),
                getRequestProperties());

        this.loggerService.Debug("RequestReponse = %message".replace("%message", response));

        return extractContentFromResponse(response).contains("Oui");
    }

    private Map<String, String> getRequestProperties() {
        return new HashMap<>() {{
           put("Content-Type", "application/json");
           put("Authorization", "Bearer " + configurationService.getChatGptApiKey());
        }};
    }
    private String getRequestPrompt(String message) {
        String requestMessage = IS_BAD_MESSAGE_REQUEST
                .replace("%testedMessage", message);

        String prompt = "[{\"role\": \"user\", \"content\": \"" + requestMessage + "\"}]";
        return "{\"model\": \"" + this.configurationService.getChatGptModel()  + "\", \"messages\": " + prompt + "}";
    }

    /**
     * Méthode permettant d'éxtraire la réponse de chatGpt depuis les réponses de la requete.
     * @param response Reponse de la requete à ChatGpt.
     * @return Retourne la phrase de reponse de ChatGpt.
     */
    private String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}

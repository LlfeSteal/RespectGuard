package fr.lifesteal.respectguard.business;

import com.google.gson.Gson;
import fr.lifesteal.respectguard.business.Interface.IChatGptService;
import fr.lifesteal.respectguard.business.config.Interface.IConfigurationService;
import fr.lifesteal.respectguard.business.Interface.IHttpRequestService;
import fr.lifesteal.respectguard.business.Interface.ILoggerService;
import fr.lifesteal.respectguard.business.object.MessageAnalysesResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * {@inheritDoc}
 */
public class ModerationService implements IChatGptService {
    private static final String OPENAI_MODERATION_URL = "https://api.openai.com/v1/moderations";
    private final IConfigurationService configurationService;
    private final IHttpRequestService httpRequestService;
    private final ILoggerService loggerService;

    /**
     * Initialise une instance de la class ChatGptService
     * @param loggerService service de gestion des logs.
     * @param configurationService service de gestion de la configuration.
     */
    public ModerationService(ILoggerService loggerService, IConfigurationService configurationService, IHttpRequestService httpRequestService) {
        this.loggerService = loggerService;
        this.configurationService = configurationService;
        this.httpRequestService = httpRequestService;
    }

    /**
     * {@inheritDoc}
     */
    public MessageAnalysesResult analyzeMessage(String message) {
        this.loggerService.Debug("RequestMessage = %message".replace("%message", message));

        String response = this.httpRequestService.getRequestResponse(
                OPENAI_MODERATION_URL,
                "POST",
                getRequestMessage(message),
                getRequestProperties());

        this.loggerService.Debug("RequestReponse = %message".replace("%message", response));

        return extractContentFromResponse(response);
    }

    private Map<String, String> getRequestProperties() {
        return new HashMap<>() {{
           put("Content-Type", "application/json");
           put("Authorization", "Bearer " + configurationService.getChatGptApiKey());
        }};
    }

    private String getRequestMessage(String message) {
        return "{\"input\": \"" + message + "\"}";
    }

    /**
     * Méthode permettant d'éxtraire la réponse de chatGpt depuis les réponses de la requete.
     * @param response Reponse de la requete à ChatGpt.
     * @return Retourne la phrase de reponse de ChatGpt.
     */
    private MessageAnalysesResult extractContentFromResponse(String response) {
        Gson gson = new Gson();
        ResultWrapper resultWrapper = gson.fromJson(response, ResultWrapper.class);
        Result result = resultWrapper.results[0];

        boolean flagged = result.flagged;

        var categories = new HashSet<String>();
        for (var category : result.categories.entrySet()) {
            if (category.getValue()) {
                categories.add(category.getKey());
            }
        }

        return new MessageAnalysesResult(flagged, categories);
    }

    private static class ResultWrapper {
        Result[] results;
    }

    private static class Result {
        boolean flagged;
        Map<String, Boolean> categories;
    }
}

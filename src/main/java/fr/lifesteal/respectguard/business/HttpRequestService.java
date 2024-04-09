package fr.lifesteal.respectguard.business;

import fr.lifesteal.respectguard.business.Interface.IHttpRequestService;
import fr.lifesteal.respectguard.business.Interface.ILoggerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequestService implements IHttpRequestService {

    private final ILoggerService loggerService;

    public HttpRequestService(ILoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /**
     * Méthode permettant d'envoyer une requete HTTP et de récupérer la réponse.
     * @param requestMessage Message à envoyer.
     * @return Retourne la réponse ou vide si une erreur s'est produite.
     */
    public String getRequestResponse(String url, String requestMethod, String requestMessage, Map<String, String> requestProperties) {
        try {
            HttpURLConnection connection  = GetConnection(url, requestMethod);
            this.addRequestProperties(connection, requestProperties);

            this.sendRequest(connection, requestMessage);
            String response = this.GetRequestResult(connection);

            this.loggerService.Debug("RawRequestReponse = %message".replace("%message", response));

            return response;
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
    private HttpURLConnection GetConnection(String requestUrl, String requestMethod) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);

        return connection;
    }

    private void addRequestProperties(HttpURLConnection connection, Map<String, String> requestProperties) {
        for (var requestPropertyKey : requestProperties.entrySet()) {
            connection.setRequestProperty(requestPropertyKey.getKey(), requestPropertyKey.getValue());
        }
    }

    /**
     * Méthode permettant de renseigner les données et d'envoyer la requete à l'API ChatGpt.
     * @param connection Connection à utiliser pour écrire les données.
     * @param requestMessage Données de la requête à envoyer.
     * @throws IOException Léve une exception si une erreur I/O se produit sur le connection.
     */
    private void sendRequest(HttpURLConnection connection, String requestMessage) throws IOException {


        this.loggerService.Debug("requestBody = %message".replace("%message", requestMessage));

        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestMessage);
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
}

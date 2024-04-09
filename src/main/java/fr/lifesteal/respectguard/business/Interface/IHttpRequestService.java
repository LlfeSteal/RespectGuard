package fr.lifesteal.respectguard.business.Interface;

import java.util.Map;

public interface IHttpRequestService {
    String getRequestResponse(String url, String requestMethod, String requestMessage, Map<String, String> requestProperties);
}

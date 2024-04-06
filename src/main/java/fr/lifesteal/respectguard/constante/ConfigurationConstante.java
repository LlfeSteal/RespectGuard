package fr.lifesteal.respectguard.constante;

import java.util.ArrayList;
import java.util.List;

public final class ConfigurationConstante {
    public static final String CHATGPT_API_KEY = "chatGPT.api-key";
    public static final String CHATGPT_API_KEY_DEFAULT_VALUE = "";
    public static final String CHATGPT_MODEL_KEY = "chatGPT.model";
    public static final String CHATGPT_MODEL_KEY_DEFAULT_VALUE = "gpt-3.5-turbo";
    public static final String EVENT_CANCEL_KEY = "event.cancel-event";
    public static final String EVENT_CANCEL_KEY_DEFAULT_VALUE = "false";
    public static final String EVENT_COMMANDS_KEY = "event.commands";
    public static final List<String> EVENT_COMMANDS_KEY_DEFAULT_VALUE = new ArrayList<>();

}

package fr.dorianosaure.RespectGuard.service;

import fr.dorianosaure.RespectGuard.service.Interface.ILoggerService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@inheritDoc}
 */
public class LoggerService implements ILoggerService {

    private final Logger logger;

    /**
     * Initialise une instance de la class ChatGptService
     * @param logger Logger de base de spigot.
     */
    public LoggerService(Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Error(String string, Exception exception) {
        this.logger.log(Level.SEVERE, string, exception);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Debug(String message) {
        this.logger.log(Level.CONFIG, message);
    }
}

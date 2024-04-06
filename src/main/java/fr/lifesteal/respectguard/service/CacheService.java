package fr.lifesteal.respectguard.service;

import fr.lifesteal.respectguard.service.Interface.ICacheService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@inheritDoc}
 */
public class CacheService implements ICacheService {

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String key, Object value) {
        this.cache.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(String key) {
        return this.cache.get(key);
    }

}

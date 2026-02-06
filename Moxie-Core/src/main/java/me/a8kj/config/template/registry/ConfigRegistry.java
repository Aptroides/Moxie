package me.a8kj.config.template.registry;

import lombok.NonNull;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.util.Pair;
import me.a8kj.util.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A thread-safe registry for managing {@link ConfigFile} instances.
 * This class acts as a central repository, allowing developers to register,
 * retrieve, and iterate over all configuration files used in the application.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class ConfigRegistry implements Registry<ConfigFile<?>> {

    /**
     * Internal storage for registered configurations.
     * Uses {@link ConcurrentHashMap} to ensure thread safety during concurrent access.
     */
    private final Map<String, ConfigFile<?>> configs = new ConcurrentHashMap<>();

    /**
     * Registers a configuration file with a unique identifying key.
     *
     * @param key   the unique identifier for the config.
     * @param value the configuration file instance.
     */
    @Override
    public void register(@NonNull String key, @NonNull ConfigFile<?> value) {
        configs.put(key, value);
    }

    /**
     * Removes a configuration from the registry.
     *
     * @param key the identifier of the config to remove.
     */
    @Override
    public void unregister(@NonNull String key) {
        configs.remove(key);
    }

    /**
     * Checks if a specific key is already registered.
     *
     * @param key the key to check.
     * @return {@code true} if present, {@code false} otherwise.
     */
    @Override
    public boolean hasEntry(@NonNull String key) {
        return configs.containsKey(key);
    }

    /**
     * Retrieves a registered configuration file.
     *
     * @param key the identifier for the config.
     * @return the {@link ConfigFile} instance.
     * @throws NullPointerException if no config is found for the given key.
     */
    @Override
    @NonNull
    public ConfigFile<?> get(@NonNull String key) {
        ConfigFile<?> config = configs.get(key);
        if (config == null) {
            throw new NullPointerException("No configuration registered with key: " + key);
        }
        return config;
    }

    /**
     * Returns an iterable collection of all registered entries as {@link Pair} objects.
     *
     * @return an iterable of key-config pairs.
     */
    @Override
    @NonNull
    public Iterable<Pair<String, ConfigFile<?>>> entries() {
        List<Pair<String, ConfigFile<?>>> list = new ArrayList<>();
        for (Map.Entry<String, ConfigFile<?>> entry : configs.entrySet()) {
            list.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        return list;
    }
}


package me.a8kj.config.context.impl;

import lombok.RequiredArgsConstructor;
import me.a8kj.config.ConfigProvider;
import me.a8kj.config.context.ConfigExecutionContext;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.operation.BasicConfigOperationExecutor;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.file.operation.ConfigOperationExecutor;
import me.a8kj.config.file.operation.impl.CRUDOperations;

import java.io.IOException;
import java.util.function.Function;

/**
 * Standard implementation of {@link ConfigExecutionContext}.
 * Bridges the gap between the file, the executor, and the CRUD operations.
 */
@RequiredArgsConstructor
public class BasicConfigExecutionContext<K> implements ConfigExecutionContext<K> {

    private final ConfigFile<K> configFile;
    private final ConfigOperationExecutor<K> executor;
    private final CRUDOperations<K> operations;

    /**
     * Static factory to create a context from a registered config key.
     */
    @SuppressWarnings("unchecked")
    public static <K> ConfigExecutionContext<K> of(String configKey, CRUDOperations<K> operations) {
        ConfigFile<K> file = (ConfigFile<K>) ConfigProvider.provide().getConfig(configKey);
        if (file == null) {
            throw new IllegalArgumentException("Config key '" + configKey + "' not found in registry!");
        }
        return new BasicConfigExecutionContext<>(file, new BasicConfigOperationExecutor<>(file), operations);
    }

    @Override
    public ConfigExecutionContext<K> execute(Function<CRUDOperations<K>, ConfigOperation<K>> task) throws IOException {
        executor.execute(task.apply(operations));
        return this;
    }

    @Override
    public ConfigFile<K> config() {
        return configFile;
    }

    @Override
    public CRUDOperations<K> operations() {
        return operations;
    }
}

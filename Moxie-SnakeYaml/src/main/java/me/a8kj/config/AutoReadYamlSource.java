package me.a8kj.config;

import lombok.NoArgsConstructor;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.logging.Log;

/**
 * Provides an automated YAML configuration source that triggers
 * a read operation immediately after creation.
 * <p>
 * Extends {@link YamlSource} to seamlessly load configuration data
 * upon file initialization without requiring manual invocation.
 * </p>
 *
 * <p>
 * Any exceptions during the auto-read process are logged using
 * {@link Log#error(String, Object...)}.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
@NoArgsConstructor
public class AutoReadYamlSource extends YamlSource {

    /**
     * Invoked immediately after the configuration file has been created.
     * <p>
     * This method automatically reads the configuration from the YAML source,
     * ensuring that the {@link ConfigFile} is populated and ready for use.
     * Any exceptions encountered during reading are caught and logged.
     * </p>
     *
     * @param config the {@link ConfigFile} context being initialized
     */
    @Override
    protected void onPostCreate(ConfigFile<String> config) {
        super.onPostCreate(config);

        try {
            this.read().execute(config);
        } catch (Exception e) {
            Log.error("Failed to auto-read YAML source '%s' after creation: %s",
                    config.meta().getName(), e.getMessage());
        }
    }
}
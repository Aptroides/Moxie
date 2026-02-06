package me.a8kj.config.file;


import lombok.NoArgsConstructor;
import me.a8kj.logging.Log;

/**
 * An extended implementation of {@link BasicBukkitConfig} that automatically
 * triggers a {@link #read()} operation immediately after the file is created.
 * <p>
 * This is ideal for configurations that should always have their memory
 * populated with existing disk data upon initialization.
 * </p>
 * * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 *
 * @since 0.1
 */
@NoArgsConstructor
public class AutoReadBukkitConfig extends BasicBukkitConfig {

    /**
     * Overrides the post-create hook to perform an automatic read operation.
     * It still maintains the event dispatching by calling super.
     *
     * @param config The configuration file instance.
     */
    @Override
    protected void onPostCreate(ConfigFile<String> config) {
        super.onPostCreate(config);

        try {
            this.read().execute(config);
        } catch (Exception e) {
            Log.error("Failed to auto-read config '%s' after creation.", config.meta().getName());
        }
    }
}

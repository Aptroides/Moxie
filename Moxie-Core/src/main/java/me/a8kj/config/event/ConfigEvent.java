package me.a8kj.config.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.eventbus.Event;

/**
 * Abstract base class for all configuration-related events.
 * This class serves as a bridge between the configuration system and the event bus,
 * ensuring that every event carries a reference to the affected configuration file.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@RequiredArgsConstructor
@Getter
public abstract class ConfigEvent<K> extends Event {

    /**
     * The configuration file instance associated with this event.
     *
     * @return the {@link ConfigFile} that triggered or is subject to this event.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    private final ConfigFile<K> configFile;
}
package me.a8kj.config.template.memory.impl;

import me.a8kj.config.template.replacement.Replacement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Velocity-specific implementation of {@link GenericMapDataMemory}.
 * <p>
 * This class extends the standard generic memory to provide native support
 * for Kyori Adventure Components and legacy color formatting.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.2
 */
public class VelocityDataMemory extends GenericMapDataMemory<String> {

    /**
     * Retrieves a value as a Kyori {@link Component} with legacy color support.
     *
     * @param key          the configuration key.
     * @param replacements optional placeholders to process before conversion.
     * @return a formatted Component, or {@link Component#empty()} if not found.
     */
    public Component getComponent(String key, Replacement... replacements) {
        String raw = getString(key);
        if (raw == null) return Component.empty();

        String processed = transform(raw, replacements);

        return LegacyComponentSerializer.legacyAmpersand().deserialize(processed);
    }

    /**
     * A specialized version of transform that directly returns a Component.
     * * @param key the key pointing to the raw string.
     * @return processed Component.
     */
    public Component getComponent(String key) {
        return getComponent(key, new Replacement[0]);
    }
}
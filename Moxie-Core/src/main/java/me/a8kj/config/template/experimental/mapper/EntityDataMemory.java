package me.a8kj.config.template.experimental.mapper;

import me.a8kj.config.template.memory.impl.GenericMapDataMemory;
import me.a8kj.config.template.replacement.ReplacementProcessor;
import me.a8kj.config.template.replacement.impl.PlaceholderProcessor;

/**
 * An implementation of {@link EntityAwareMemory} that integrates complex entity mapping
 * with standard configuration memory management.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * It extends {@link GenericMapDataMemory} to provide full support for path-based
 * data storage while delegating entity-specific logic to a {@link MapperOperatorContract}.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public class EntityDataMemory extends GenericMapDataMemory<String> implements EntityAwareMemory {

    private final MapperOperatorContract mapperService;
    private final ReplacementProcessor processor;

    /**
     * Constructs an {@code EntityDataMemory} with a specified mapper service and replacement processor.
     *
     * @param mapperService the service used for serializing and deserializing entities.
     * @param processor     the processor responsible for handling dynamic string placeholders.
     */
    public EntityDataMemory(MapperOperatorContract mapperService, ReplacementProcessor processor) {
        this.mapperService = mapperService;
        this.processor = processor;
    }

    /**
     * Constructs an {@code EntityDataMemory} with a specified mapper service and
     * default percent-style placeholders (e.g., %key%).
     *
     * @param mapperService the service used for serializing and deserializing entities.
     */
    public EntityDataMemory(MapperOperatorContract mapperService) {
        this(mapperService, PlaceholderProcessor.percent());
    }

    /**
     * {@inheritDoc}
     * * @return the internal mapper operator used for entity transformations.
     */
    @Override
    public MapperOperatorContract getMapperService() {
        return mapperService;
    }

    /**
     * {@inheritDoc}
     * * @return the internal replacement processor used for string manipulation.
     */
    @Override
    public ReplacementProcessor getProcessor() {
        return processor;
    }
}
package me.a8kj.config.template.experimental.mapper;

/**
 * A standard, out-of-the-box implementation of {@link MapperOperatorContract}.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * This class inherits the core mapping logic from {@link BaseMapperOperator}, providing
 * a concrete entry point for entity serialization and deserialization using the
 * registered codecs.</p>
 * * <p>It can be used directly or extended if specialized behavior is required for
 * the default mapping process.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public class DefaultMapperOperator extends BaseMapperOperator {
}
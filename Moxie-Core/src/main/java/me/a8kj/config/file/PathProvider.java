package me.a8kj.config.file;

import java.io.File;
import java.nio.file.Path;

/**
 * Provides a base directory context for resolving configuration file paths.
 * This interface abstracts the file system location, allowing configurations
 * to be stored relative to a specific root folder.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface PathProvider {

    /**
     * Retrieves the root directory from which all configuration paths are resolved.
     *
     * @return the base {@link Path} directory.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    Path getBaseDirectory();

    /**
     * Resolves a relative path string against the base directory to produce a {@link File} object.
     *
     * @param relativePath the relative path to the configuration file.
     * @return a {@link File} object representing the resolved path.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default File resolve(String relativePath) {
        return getBaseDirectory().resolve(relativePath).toFile();
    }
}
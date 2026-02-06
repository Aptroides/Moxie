package me.a8kj.config.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class providing common factory methods for creating {@link PathProvider} instances.
 * This class offers various strategies for determining the base directory where configurations should be stored.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public final class PathProviders {

    /**
     * Creates a provider that uses the current working directory of the application.
     *
     * @return a {@link PathProvider} pointing to the current absolute path.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider currentDir() {
        return () -> Paths.get("").toAbsolutePath();
    }

    /**
     * Creates a provider that points to the current user's home directory.
     *
     * @return a {@link PathProvider} pointing to the 'user.home' system property path.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider userDir() {
        return () -> Paths.get(System.getProperty("user.home"));
    }

    /**
     * Creates a provider that points to the directory where the JAR file of the specified class is located.
     * If the location cannot be determined, it fallbacks to the current working directory.
     *
     * @param mainClass the class used to locate the JAR's source code source.
     * @return a {@link PathProvider} pointing to the JAR's parent directory.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider jarDir(Class<?> mainClass) {
        return () -> {
            try {
                return Paths.get(mainClass.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            } catch (Exception e) {
                return currentDir().getBaseDirectory();
            }
        };
    }

    /**
     * Creates a provider that uses a custom string-based absolute path.
     *
     * @param path the string representing the custom directory path.
     * @return a {@link PathProvider} pointing to the specified path.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider custom(String path) {
        return () -> Paths.get(path).toAbsolutePath();
    }

    /**
     * Creates a provider that points to the system's default temporary directory.
     *
     * @return a {@link PathProvider} pointing to the 'java.io.tmpdir' path.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider tempDir() {
        return () -> Paths.get(System.getProperty("java.io.tmpdir"));
    }

    /**
     * Creates a hybrid provider that attempts to use a primary directory if it exists and is writable,
     * otherwise falls back to a secondary directory.
     *
     * @param primary the first choice {@link PathProvider}.
     * @param secondary the fallback {@link PathProvider}.
     * @return a {@link PathProvider} that evaluates the best available directory.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider hybrid(PathProvider primary, PathProvider secondary) {
        return () -> {
            Path primaryPath = primary.getBaseDirectory();

            if (Files.exists(primaryPath) && Files.isWritable(primaryPath)) {
                return primaryPath;
            }

            return secondary.getBaseDirectory();
        };
    }

    /**
     * Creates a hybrid provider that prioritizes the JAR's directory and falls back to
     * the user's home directory if the JAR directory is not accessible/writable.
     *
     * @param mainClass the class used to locate the JAR.
     * @return a {@link PathProvider} with a jar-to-user fallback strategy.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static PathProvider jarWithUserFallback(Class<?> mainClass) {
        return hybrid(jarDir(mainClass), userDir());
    }
}
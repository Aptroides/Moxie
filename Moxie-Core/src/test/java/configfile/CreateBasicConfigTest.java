package configfile;

import me.a8kj.config.builder.BasicConfigBuilder;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.PathProviders;
import me.a8kj.config.file.properties.BasicConfigMeta;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;

/**
 * Basic integration test to demonstrate the creation of a {@link ConfigFile} instance.
 * This test highlights the use of the Fluent Builder and the Hybrid Path Provider,
 * which ensures portability between development environments and production JAR deployments.
 */
public class CreateBasicConfigTest {

    /**
     * Entry point for validating the construction of a basic configuration entity.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        /*
         * 1. Building the ConfigFile Entity.
         * The builder defines the data type (String-based keys), metadata (internal name
         * and relative file path), and the memory strategy for runtime storage.
         */
        ConfigFile<String> config = BasicConfigBuilder.of(String.class)
                .meta(BasicConfigMeta.builder()
                        .name("network")
                        .relativePath("settings.yml")
                        .build())
                /*
                 * Utilizing the Hybrid Path Provider:
                 * Priority 1: Attempts to locate or create the file relative to the JAR location.
                 * Priority 2: Falls back to a user-defined directory if JAR-relative access is restricted.
                 */
                .at(PathProviders.jarWithUserFallback(CreateBasicConfigTest.class))
                .memory(new MapPairedDataMemory())
                .build();

        /*
         * Note: At this stage, the object exists in memory. No physical file operations
         * occur until a ConfigOperationExecutor executes a CRUD operation.
         */
    }
}
package experimental.mapper;

import me.a8kj.config.template.experimental.codec.CodecAdapter;
import me.a8kj.config.template.experimental.mapper.DefaultMapperOperator;
import me.a8kj.config.template.experimental.mapper.EntityDataMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A demonstration test class for the experimental entity mapping system.
 * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.</p>
 * <p>
 * This test validates the bidirectional flow:
 * 1. Serializing a domain object into flattened memory keys.
 * 2. Reconstructing the domain object from those keys using a registered codec.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public class MapperTest {

    public static void main(String[] args) {
        // 1. Initialize the professional Mapper Operator (Experimental)
        DefaultMapperOperator mapper = new DefaultMapperOperator();

        // 2. Register a Codec for the User record
        // Uses the RequiredArgsConstructor of CodecAdapter to pass functional Serializer/Deserializer
        mapper.registerCodec(User.class, new CodecAdapter<>(
                entity -> {
                    if (entity == null) return null;
                    Map<String, Object> map = new HashMap<>();
                    map.put("username", entity.username());
                    map.put("level", entity.level());
                    return map;
                },
                map -> {
                    if (map == null || map.isEmpty()) return null;
                    return new User(
                            (String) map.get("username"),
                            (int) map.get("level")
                    );
                }
        ));

        // 3. Initialize the EntityDataMemory
        // Leverages the GenericMapDataMemory logic for internal storage
        EntityDataMemory memory = new EntityDataMemory(mapper);

        // 4. Test Entity Storage (The "Save" phase)
        User user = new User("PlayerOne", 10);
        memory.setEntity("users.player1", user);

        // 5. Verification: Accessing raw keys created by the flattening process
        System.out.println("--- Raw Memory Verification ---");
        System.out.println("Path [users.player1.username]: " + memory.getString("users.player1.username"));
        System.out.println("Path [users.player1.level]: " + memory.getInt("users.player1.level", 0));

        // 6. Test Entity Retrieval (The "Load" phase - Unflattening + Deserialization)
        System.out.println("\n--- Entity Reconstruction ---");
        Optional<User> loadedUser = memory.getEntity("users.player1", User.class);

        loadedUser.ifPresentOrElse(
                u -> System.out.println("Reconstructed -> Name: " + u.username() + ", Level: " + u.level()),
                () -> System.err.println("Error: Entity not found or deserialization failed!")
        );
    }
}

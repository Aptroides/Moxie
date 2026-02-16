package me.a8kj.util;

import lombok.experimental.UtilityClass;
import me.a8kj.config.template.memory.DataMemory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for transforming between hierarchical and flat map structures.
 * <p>
 * Provides methods to flatten nested {@link Map} structures into dot-notated
 * key/value pairs compatible with {@link DataMemory} and to reconstruct
 * nested maps from such flat representations.
 * </p>
 * <p>
 * Useful for YAML/JSON processing and in-memory configuration management.
 * </p>
 *
 * <p>
 * This is a utility class and cannot be instantiated.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
@UtilityClass
public final class MapStructureUtils {

    /**
     * Flattens a nested {@link Map} into a flat structure using dot-separated keys.
     * <p>
     * Nested maps are recursively processed, and non-map values are stored in
     * the provided {@link DataMemory} instance.
     * </p>
     *
     * @param prefix the current key prefix (use empty string for top-level)
     * @param source the nested map to flatten
     * @param memory the target {@link DataMemory} to store flattened entries
     */
    @SuppressWarnings("unchecked")
    public static void flatten(String prefix, Map<String, Object> source, DataMemory<String> memory) {
        if (source == null) return;

        source.forEach((key, value) -> {
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;

            if (value instanceof Map<?, ?> subMap) {
                flatten(fullKey, (Map<String, Object>) subMap, memory);
            } else {
                memory.set(fullKey, value);
            }
        });
    }

    /**
     * Reconstructs a nested {@link Map} from a flat map with dot-separated keys.
     * <p>
     * Each key in the flat map is split by dots to create hierarchical nesting
     * of maps. Original values are preserved at leaf nodes.
     * </p>
     *
     * @param flatMap the flat map containing dot-notated keys
     * @return a nested {@link Map} representing the original hierarchy
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> unflatten(Map<String, Object> flatMap) {
        if (flatMap == null) return new LinkedHashMap<>();

        Map<String, Object> result = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : flatMap.entrySet()) {
            String[] keys = entry.getKey().split("\\.");
            Map<String, Object> current = result;

            for (int i = 0; i < keys.length - 1; i++) {
                Object next = current.computeIfAbsent(keys[i], k -> new LinkedHashMap<String, Object>());
                if (next instanceof Map) {
                    current = (Map<String, Object>) next;
                }
            }
            current.put(keys[keys.length - 1], entry.getValue());
        }
        return result;
    }


    /**
     * Extracts a nested map from a DataMemory under a specific path.
     *
     * @param path   the root path to extract
     * @param memory the DataMemory instance
     * @return a nested map of values under the path
     */
    public static Map<String, Object> unflattenUnderPath(String path, DataMemory<String> memory) {
        String prefix = path + ".";

        Map<String, Object> rawData = memory.getKeys(path, true).stream()
                .filter(key -> key.startsWith(prefix))
                .collect(Collectors.toMap(
                        key -> key.substring(prefix.length()),
                        memory::get,
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));

        if (rawData.isEmpty() && memory.contains(path)) {
            Object value = memory.get(path);
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            }
        }

        return unflatten(rawData);
    }
}
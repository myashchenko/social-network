package ua.social.network.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
public class ReflectionUtils {

    private ReflectionUtils() {}

    public static <T> List<String> getFieldNames(Class<T> clazz) {
        List<String> result = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            result.add(field.getName());
        }
        return result;
    }

    public static Map<String, Object> getFieldNameValueMap(Class<?> clazz, Object object) {
        Map<String, Object> result = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                result.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to get value from field " + field.getName(), e);
            }
        }
        return result;
    }
}

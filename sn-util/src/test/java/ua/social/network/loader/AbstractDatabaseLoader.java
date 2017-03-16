package ua.social.network.loader;

import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author Mykola Yashchenko
 */
public class AbstractDatabaseLoader implements DatabaseLoader {

    @Override
    public void load(Class<?> testClass, String location) {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(testClass);
        Resource resource = resourceLoader.getResource(location);
        if (resource.exists()) {
            // todo
        }
    }
}

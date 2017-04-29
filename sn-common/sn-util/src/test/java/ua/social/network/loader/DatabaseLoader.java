package ua.social.network.loader;

/**
 * @author Mykola Yashchenko
 */
public interface DatabaseLoader {
    void load(Class<?> testClass, String location);
}

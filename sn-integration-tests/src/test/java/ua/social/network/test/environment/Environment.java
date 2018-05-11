package ua.social.network.test.environment;

/**
 * @author Mykola Yashchenko
 */
public interface Environment {
    String baseHost();
    void beforeTests();
    void afterTests();
}

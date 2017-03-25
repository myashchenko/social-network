package ua.social.network.exception;

/**
 * @author Mykola Yashchenko
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message, String... params) {
        super(String.format(message, (Object[]) params));
    }
}

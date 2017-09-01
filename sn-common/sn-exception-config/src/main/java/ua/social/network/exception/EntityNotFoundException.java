package ua.social.network.exception;

/**
 * @author Mykola Yashchenko
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message, String... values) {
        super(String.format(message, (Object[]) values));
    }

    public EntityNotFoundException(String message, Throwable root) {
        super(message, root);
    }
}

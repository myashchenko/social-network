package ua.social.network.exception;

/**
 * @author Mykola Yashchenko
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}

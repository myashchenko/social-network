package ua.social.network.exception;

/**
 * @author Mykola Yashchenko
 */
public class StorageException extends RuntimeException {

    public StorageException(String message, String... values) {
        super(String.format(message, (Object[]) values));
    }

    public StorageException(String message, Throwable root) {
        super(message, root);
    }
}

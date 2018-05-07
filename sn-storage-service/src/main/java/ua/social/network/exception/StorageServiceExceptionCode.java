package ua.social.network.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Mykola Yashchenko
 */
public enum StorageServiceExceptionCode implements SnExceptionDetails {
    FILE_NOT_FOUND("SS-001", HttpStatus.NOT_FOUND),
    STORAGE_EXCEPTION("SS-002", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus status;

    StorageServiceExceptionCode(final String code, final HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public HttpStatus status() {
        return status;
    }
}

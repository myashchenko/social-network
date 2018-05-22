package ua.social.network.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Mykola Yashchenko
 */
public enum UserQueryServiceExceptionDetails implements SnExceptionDetails {

    NOT_FOUND("US-001", HttpStatus.NOT_FOUND);

    private final String code;
    private final HttpStatus status;

    UserQueryServiceExceptionDetails(final String code, final HttpStatus status) {
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

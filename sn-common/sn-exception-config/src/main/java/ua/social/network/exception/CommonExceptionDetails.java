package ua.social.network.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Mykola Yashchenko
 */
public enum CommonExceptionDetails implements SnExceptionDetails {
    UNHANDLED_EXCEPTION("SN-000", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_FIELD("SN-001", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus status;

    CommonExceptionDetails(final String code, final HttpStatus status) {
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

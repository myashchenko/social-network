package ua.social.network.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Mykola Yashchenko
 */
public interface SnExceptionDetails {
    String code();
    HttpStatus status();
}

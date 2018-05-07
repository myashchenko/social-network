package ua.social.network.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.social.network.api.Error;
import ua.social.network.api.ErrorResponse;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.exception.SnException;
import ua.social.network.exception.StorageException;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class CommonExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String UNHANDLED_EXCEPTION_CODE = "SN-000";

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(StorageException.class)
    public void handleStorageException(StorageException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(SnException.class)
    public ResponseEntity<Object> handleSnException(final SnException ex, final Locale locale) {
        log.error(ex.getMessage(), ex);

        final String message = messageSource.getMessage(ex, locale);
        final String code = ex.exceptionDetails().code();
        final ErrorResponse errorResponse = new ErrorResponse(new Error(message, code));

        return ResponseEntity.status(ex.exceptionDetails().status().value()).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers,
                                                             final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);

        final ErrorResponse errorResponse = new ErrorResponse(new Error(ex.getMessage(), UNHANDLED_EXCEPTION_CODE));
        return ResponseEntity.status(status.value()).body(errorResponse);
    }
}

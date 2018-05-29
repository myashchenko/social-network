package ua.social.network.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.social.network.api.Error;
import ua.social.network.api.ErrorResponse;
import ua.social.network.exception.CommonExceptionDetails;
import ua.social.network.exception.SnException;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class CommonExceptionHandlerController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(SnException.class)
    public ResponseEntity<Object> handleSnException(final SnException ex, final Locale locale) {
        log.error(ex.getMessage(), ex);

        final String message = messageSource.getMessage(ex, locale);
        final String code = ex.exceptionDetails().code();
        final ErrorResponse errorResponse = new ErrorResponse(new Error(message, code));

        return ResponseEntity.status(ex.exceptionDetails().status().value()).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);

        final FieldError fieldError = ex.getBindingResult().getFieldError();
        final String code = fieldError.getDefaultMessage();
        final String message = messageSource.getMessage(code, fieldError.getArguments(), request.getLocale());
        final ErrorResponse response = new ErrorResponse(new Error(message, code));
        return ResponseEntity.status(status).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers,
                                                             final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);

        final ErrorResponse errorResponse = new ErrorResponse(new Error(ex.getMessage(), CommonExceptionDetails.UNHANDLED_EXCEPTION.code()));
        return ResponseEntity.status(status.value()).body(errorResponse);
    }
}

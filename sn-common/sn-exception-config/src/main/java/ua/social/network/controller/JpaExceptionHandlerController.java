package ua.social.network.controller;

import java.util.Locale;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.social.network.api.Constraint;
import ua.social.network.api.Error;
import ua.social.network.api.ErrorResponse;
import ua.social.network.exception.SnExceptionDetails;

import static ua.social.network.exception.UserServiceExceptionDetails.FRIEND_REQUEST_HAS_SENT_ALREADY;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
@ConditionalOnClass(DataIntegrityViolationException.class)
public class JpaExceptionHandlerController {

    private static final String UNHANDLED_EXCEPTION_CODE = "SN-000";

    private final MessageSource messageSource;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleIntegrityViolation(final DataIntegrityViolationException ex, final Locale locale) {
        log.error(ex.getMessage(), ex);

        if (ex.getCause() instanceof ConstraintViolationException) {
            final Constraint constraint = Constraint.of(ex.getCause().getCause().getMessage());
            if (constraint != null) {
                switch (constraint) {
                    case FRIEND_REQUEST_FROM_TO:
                        final SnExceptionDetails exceptionDetails = FRIEND_REQUEST_HAS_SENT_ALREADY;
                        final String message = messageSource.getMessage(exceptionDetails.code(), new Object[0], locale);
                        final ErrorResponse response = new ErrorResponse(new Error(
                                message, exceptionDetails.code()));
                        return ResponseEntity.status(exceptionDetails.status()).body(response);
                    default:
                        break;
                }
            }
        }

        final ErrorResponse errorResponse = new ErrorResponse(new Error(ex.getMessage(), UNHANDLED_EXCEPTION_CODE));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

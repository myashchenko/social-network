package ua.social.network.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.exception.StorageException;

/**
 * @author Mykola Yashchenko
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandlerController {

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
}

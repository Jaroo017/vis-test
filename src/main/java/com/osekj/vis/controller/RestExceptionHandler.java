package com.osekj.vis.controller;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleNotFoundException() {
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public void handleForbiddenException() {
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {IllegalArgumentException.class, NullPointerException.class, ValidationException.class,
            JsonParseException.class})
    public void handleInternalException() {
    }
}

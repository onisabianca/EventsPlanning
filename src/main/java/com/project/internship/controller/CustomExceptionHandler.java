package com.project.internship.controller;

import com.project.internship.exception.DuplicateKeyConstraintException;
import com.project.internship.exception.InvalidCredentialsException;
import com.project.internship.exception.NotAvailableException;
import com.project.internship.exception.NotFoundException;
import com.project.internship.exception.NotPermittedException;
import com.project.internship.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.info("Resource not found! {}", e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getClass(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyConstraintException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleSqlConstraintException(DuplicateKeyConstraintException e) {
        log.info("Duplicate entry! {}", e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.toString(), e.getClass(), e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException e) {
        log.info("Invalid credentials! {}", e.getMessage());
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), e.getClass(), e.getMessage());
    }

    @ExceptionHandler(NotPermittedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotPermittedException(NotPermittedException e) {
        log.info("Forbidden access! {}", e.getMessage());
        return new ErrorResponse(HttpStatus.FORBIDDEN.toString(), e.getClass(), e.getMessage());
    }

    @ExceptionHandler(NotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotAvailableException(NotAvailableException e) {
        log.info("Not available! {}", e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getClass(), e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .collect(Collectors.toList());

        log.info("Given fields are incorrect! {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getClass(), "Given fields are incorrect!", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAnyException(Exception e) {
        log.error("Exception found! {}", e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getClass(), e.getMessage());
    }

}

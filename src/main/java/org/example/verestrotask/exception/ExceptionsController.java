package org.example.verestrotask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(ClientExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> duplicateClient(ClientExistException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message",ex.getMessage());
        return response;
    }
}

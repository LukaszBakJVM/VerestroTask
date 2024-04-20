package org.example.verestrotask.exception;

public class ClientExistException extends RuntimeException {
    public ClientExistException(String message) {
        super(message);
    }
}

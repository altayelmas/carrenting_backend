package com.se.carrenting_backend.exception;

public class NotAvailableException extends RuntimeException{
    public NotAvailableException(String message) {
        super(message);
    }
}

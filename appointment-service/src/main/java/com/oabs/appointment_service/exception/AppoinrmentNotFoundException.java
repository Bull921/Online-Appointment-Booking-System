package com.oabs.appointment_service.exception;

public class AppoinrmentNotFoundException extends RuntimeException {
    public AppoinrmentNotFoundException(String message) {
        super(message);
    }
}

package com.oabs.patient_service.exception;

public class PatientIdNotFoundException extends RuntimeException {
    public PatientIdNotFoundException(String s) {
        super(s);
    }
}

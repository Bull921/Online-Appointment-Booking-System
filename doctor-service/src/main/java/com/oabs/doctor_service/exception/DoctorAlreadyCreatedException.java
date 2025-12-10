package com.oabs.doctor_service.exception;

public class DoctorAlreadyCreatedException extends RuntimeException {
    public DoctorAlreadyCreatedException(String s) {
        super(s);
    }
}

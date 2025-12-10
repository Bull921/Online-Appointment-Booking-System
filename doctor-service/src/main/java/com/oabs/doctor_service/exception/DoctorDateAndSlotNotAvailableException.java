package com.oabs.doctor_service.exception;

public class DoctorDateAndSlotNotAvailableException extends RuntimeException {
    public DoctorDateAndSlotNotAvailableException(String dateAndSlotNotAvailable) {
        super(dateAndSlotNotAvailable);
    }
}

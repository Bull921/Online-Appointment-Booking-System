package com.oabs.patient_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(PatientAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlePatientAlreadyExistException(PatientAlreadyExistException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PatientIdNotFoundException.class)
    public ResponseEntity<?> handlePatientIdNotFoundException(PatientIdNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

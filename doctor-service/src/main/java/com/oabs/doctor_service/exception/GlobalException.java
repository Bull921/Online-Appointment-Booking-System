package com.oabs.doctor_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<?> handelDoctorNotFoundException(DoctorNotFoundException ex){
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorDateAndSlotNotAvailableException.class)
    public ResponseEntity<?> handelDoctorDateAndSlotNotAvailableException(DoctorDateAndSlotNotAvailableException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorAlreadyCreatedException.class)
    public ResponseEntity<?> handelDoctorAlreadyCreatedException(DoctorAlreadyCreatedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

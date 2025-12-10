package com.oabs.prescription_service.controller;

import com.oabs.prescription_service.model.PrescriptionDetailsDto;
import com.oabs.prescription_service.model.PrescriptionRequestDto;
import com.oabs.prescription_service.model.PrescriptionResponseDto;
import com.oabs.prescription_service.service.PrescriptionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@Tag(name = "Doctors write and patients view prescriptions.")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Operation(summary = "Add a new prescription")
    @PostMapping
    @CircuitBreaker(name = "patientService", fallbackMethod = "patientFallback")
    public ResponseEntity<PrescriptionResponseDto> addPrescription(@RequestBody PrescriptionRequestDto
                                                                   prescriptionRequestDto){
        return new ResponseEntity<>(prescriptionService.addPrescription(prescriptionRequestDto),
                HttpStatus.CREATED);
    }
    public ResponseEntity<?> patientFallback(Exception ex ){
        PrescriptionResponseDto prescriptionResponseDto=new PrescriptionResponseDto("Dummy-1234",
                "This Doctor Created Dummy Prescription because some services are down..."
        );
        return new ResponseEntity<>(prescriptionResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get prescription by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDetailsDto> getPrescriptionById(@PathVariable String id){
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @Operation(summary = "Get prescription by appointment ID")
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getPrescriptionByAppointmentId(@PathVariable String appointmentId){
        return ResponseEntity.ok(prescriptionService.getPrescriptionByAppointmentId(appointmentId));
    }

    @Operation(summary = "Get all prescriptions for a patient")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPrescriptionByPatientId(@PathVariable String patientId){
        return ResponseEntity.ok(prescriptionService.getPrescriptionByPatientId(patientId));
    }

    @Operation(summary = "Delete all prescriptions for a patient")
    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<?> deletePrescriptionByPatientId(@PathVariable String patientId){
        return ResponseEntity.ok(prescriptionService.deletePrescriptionByPatientId(patientId));
    }
}

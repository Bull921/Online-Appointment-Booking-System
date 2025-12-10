package com.oabs.patient_service.controller;

import com.oabs.patient_service.model.PatientDetailsDto;
import com.oabs.patient_service.model.PatientRequestDto;
import com.oabs.patient_service.model.PatientResponseDto;
import com.oabs.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patient Service APIs", description = "Handles patient registration and profile management.")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(summary = "Register new patient")
    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto patientRequestDto){
        return new ResponseEntity<PatientResponseDto>(patientService.registerPatient(patientRequestDto),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get patient profile by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailsDto> getPatientById(@PathVariable String id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Operation(summary = "Update patient details")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatientById(@PathVariable String id,
                                                               @RequestBody PatientRequestDto requestBody){
        PatientResponseDto patientResponseDto = patientService.updatePatientById(id, requestBody);
        return ResponseEntity.ok(patientResponseDto);
    }

    @Operation(summary = "Delete patient account")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientById(@PathVariable String id){
        return ResponseEntity.ok(patientService.deletePatientById(id));
    }

    @Operation(summary = "Get patient profile by Email")
    @GetMapping("/email")
    public ResponseEntity<PatientDetailsDto> getPatientByEmail(@RequestParam String email){
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }
}

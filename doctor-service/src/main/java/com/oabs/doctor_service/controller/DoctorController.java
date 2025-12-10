package com.oabs.doctor_service.controller;

import com.oabs.doctor_service.model.AvailabilityDto;
import com.oabs.doctor_service.model.Doctor;
import com.oabs.doctor_service.model.DoctorRequestDto;
import com.oabs.doctor_service.model.DoctorResponseDto;
import com.oabs.doctor_service.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Handles doctor creation, profile updates, and availability.")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Add a new doctor")
    @PostMapping
    public ResponseEntity<DoctorResponseDto> addDoctor(@RequestBody DoctorRequestDto doctorRequestDto){
        return new ResponseEntity<>(doctorService.addDoctor(doctorRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get list of all doctors")
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctor(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @Operation(summary = "Get doctor by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable String id){
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @Operation(summary = "Update doctor profile")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctorById(@PathVariable String id,
                                              @RequestBody DoctorRequestDto doctorRequestDto){
        return ResponseEntity.ok(doctorService.updateDoctorById(id,doctorRequestDto));
    }

    @Operation(summary = "Delete doctor")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable String id){
        return ResponseEntity.ok(doctorService.deleteDoctorById(id));
    }

    @Operation(summary = "Add availability for a doctor")
    @PostMapping("/{id}/availability")
    public ResponseEntity<?> addDoctorAvailability(@PathVariable String id,
                                                   @RequestBody AvailabilityDto availabilityDto){
        return ResponseEntity.ok(doctorService.addAvailability(id, availabilityDto));
    }

    @Operation(summary = "Get availability slots for a doctor")
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getDoctorAvailability(@PathVariable String id){
        return ResponseEntity.ok(doctorService.getAvailability(id));
    }

    @Operation(summary = "Get availability slots for a doctor with date and slot")
    @GetMapping("/{id}/availability/{date}")
    public ResponseEntity<?> getDoctorAvailability(@PathVariable String id,
                                                   @PathVariable LocalDate date,
                                                   @RequestParam String availableSlots){
        return ResponseEntity.ok(doctorService.getAvailabilityByDate(id,date,availableSlots));
    }
}

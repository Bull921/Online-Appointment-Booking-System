package com.oabs.appointment_service.controller;

import com.oabs.appointment_service.model.AppointDetailsDto;
import com.oabs.appointment_service.model.AppointmentRequestDto;
import com.oabs.appointment_service.model.AppointmentResponseDto;
import com.oabs.appointment_service.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Handles booking, canceling, and viewing appointments.")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Book a new appointment")
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> bookAppoint(@RequestBody AppointmentRequestDto appointmentRequestDto){
        return ResponseEntity.ok(appointmentService.bookAppointment(appointmentRequestDto));
    }

    @Operation(summary = "Get appointment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AppointDetailsDto> getAppointment(@PathVariable String id){
        return ResponseEntity.ok(appointmentService.getByAppointmentId(id));
    }

    @Operation(summary = "Get appointments for a patient")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentByPatientId(@PathVariable String patientId){
        return ResponseEntity.ok(appointmentService.getByPatientId(patientId));
    }

    @Operation(summary = "Get appointments for a doctor")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAppointmentByDoctorId(@PathVariable String doctorId){
        return ResponseEntity.ok(appointmentService.getByDoctorId(doctorId));
    }

    @Operation(summary = "Cancel appointment")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointmentByAppointmentId(@PathVariable String id){
        return ResponseEntity.ok(appointmentService.deleteByAppointmentId(id));
    }

    @Operation(summary = "Reschedule an appointment")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointmentByAppointmentId(@PathVariable String id,
                                                   @RequestBody AppointmentRequestDto appointmentRequestDto){
        return ResponseEntity.ok(appointmentService.updateByAppointmentId(id,appointmentRequestDto));
    }
}

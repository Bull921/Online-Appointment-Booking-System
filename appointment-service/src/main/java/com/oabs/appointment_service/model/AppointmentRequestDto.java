package com.oabs.appointment_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDto {
    private String patientId;
    private String doctorId;
    private LocalDate appointmentDate;
    private String availableSlots;
}

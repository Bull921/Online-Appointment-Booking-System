package com.oabs.patient_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailsDto {
    private String patientId;
    private String name;
    private String email;
    private String mobile;
    private LocalDate dateOfBirth;
}

package com.oabs.patient_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
    private String name;
    private String email;
    private String password;
    private String mobile;
    private LocalDate dateOfBirth;
}

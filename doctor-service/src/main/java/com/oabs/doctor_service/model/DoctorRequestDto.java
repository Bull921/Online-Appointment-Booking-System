package com.oabs.doctor_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDto {
    private String name;
    private String email;
    private String specialization;
    private String mobile;
}

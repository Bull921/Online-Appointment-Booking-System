package com.oabs.doctor_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAvailabilityDto {
    private List<AvailabilityDto> availability;
}

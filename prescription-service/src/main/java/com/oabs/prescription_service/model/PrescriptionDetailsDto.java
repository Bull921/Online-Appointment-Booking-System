package com.oabs.prescription_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDetailsDto {

    private String prescriptionId;
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private String diagnosis;
    private List<MedicineDto> medicines;
    private String notes;
}

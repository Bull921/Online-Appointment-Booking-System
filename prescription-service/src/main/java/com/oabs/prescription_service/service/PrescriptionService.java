package com.oabs.prescription_service.service;

import com.oabs.prescription_service.model.PrescriptionDetailsDto;
import com.oabs.prescription_service.model.PrescriptionRequestDto;
import com.oabs.prescription_service.model.PrescriptionResponseDto;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponseDto addPrescription(PrescriptionRequestDto prescriptionRequestDto);
    PrescriptionDetailsDto getPrescriptionById(String prescriptionId);
    PrescriptionDetailsDto getPrescriptionByAppointmentId(String appointmentId);
    List<PrescriptionDetailsDto> getPrescriptionByPatientId(String patientId);
    String deletePrescriptionByPatientId(String patientId);
}

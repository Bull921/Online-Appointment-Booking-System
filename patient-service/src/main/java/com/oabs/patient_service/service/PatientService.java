package com.oabs.patient_service.service;

import com.oabs.patient_service.model.PatientDetailsDto;
import com.oabs.patient_service.model.PatientRequestDto;
import com.oabs.patient_service.model.PatientResponseDto;

public interface PatientService {
    PatientResponseDto registerPatient(PatientRequestDto patientRequestDto);
    PatientDetailsDto getPatientById(String patientId);
    PatientResponseDto updatePatientById(String patientId, PatientRequestDto patientRequestDto);
    PatientResponseDto deletePatientById(String patientId);
    PatientDetailsDto getPatientByEmail(String email);
}

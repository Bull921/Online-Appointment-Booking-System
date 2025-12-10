package com.oabs.patient_service.service.impl;

import com.oabs.patient_service.exception.PatientAlreadyExistException;
import com.oabs.patient_service.exception.PatientIdNotFoundException;
import com.oabs.patient_service.model.Patient;
import com.oabs.patient_service.model.PatientDetailsDto;
import com.oabs.patient_service.model.PatientRequestDto;
import com.oabs.patient_service.model.PatientResponseDto;
import com.oabs.patient_service.repository.PatientRepository;
import com.oabs.patient_service.service.PatientService;
import com.oabs.patient_service.service.PrescriptionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionClient prescriptionClient;

    @Override
    public PatientResponseDto registerPatient(PatientRequestDto patientRequestDto) {
        Patient patientExist = patientRepository.findByEmail(patientRequestDto.getEmail());
        if(patientExist!=null){
            throw new PatientAlreadyExistException("Patient Already Exist with Email : " +patientRequestDto.getEmail());
        }
        String message = "Patient registered successfully";
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        Patient patient = new Patient(patientId,patientRequestDto.getName(), patientRequestDto.getEmail(),
                patientRequestDto.getPassword(), patientRequestDto.getMobile(), patientRequestDto.getDateOfBirth());
        patientRepository.save(patient);
        return new PatientResponseDto(patientId,message);
    }

    @Override
    public PatientDetailsDto getPatientById(String patientId) {
        Patient patient =patientRepository.findById(patientId)
                .orElseThrow(()->new PatientIdNotFoundException("Patient Id : "+patientId+" Not Found"));
        return new PatientDetailsDto(patientId, patient.getName(),patient.getEmail(),
                patient.getMobile(), patient.getDateOfBirth());
    }

    @Override
    public PatientResponseDto updatePatientById(String patientId, PatientRequestDto patientRequestDto) {
        Patient patient =patientRepository.findById(patientId)
                .orElseThrow(()->new PatientIdNotFoundException("Patient Id : "+patientId+" Not Found"));
        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setPassword(patientRequestDto.getPassword());
        patient.setMobile(patientRequestDto.getMobile());
        patient.setDateOfBirth(patientRequestDto.getDateOfBirth());
        patientRepository.save(patient);
        return new PatientResponseDto(patientId, "Patient Updated successfully");
    }

    @Override
    public PatientResponseDto deletePatientById(String patientId) {
        Patient patient =patientRepository.findById(patientId)
                .orElseThrow(()->new PatientIdNotFoundException("Patient Id : "+patientId+" Not Found"));
        patientRepository.deleteById(patientId);
        prescriptionClient.deletePrescriptionByPatient(patientId);
        return new PatientResponseDto(patientId,"Patient Deleted successfully");
    }

    @Override
    public PatientDetailsDto getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email);
        if(patient==null){
            throw new PatientIdNotFoundException("Patient Email : " +email+" Not Found.");
        }
        return new PatientDetailsDto(patient.getPatientId(), patient.getName(),patient.getEmail(),
                patient.getMobile(), patient.getDateOfBirth());
    }
}

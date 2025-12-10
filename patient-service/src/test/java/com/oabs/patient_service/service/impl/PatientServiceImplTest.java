package com.oabs.patient_service.service.impl;

import com.oabs.patient_service.exception.PatientAlreadyExistException;
import com.oabs.patient_service.exception.PatientIdNotFoundException;
import com.oabs.patient_service.model.Patient;
import com.oabs.patient_service.model.PatientDetailsDto;
import com.oabs.patient_service.model.PatientRequestDto;
import com.oabs.patient_service.model.PatientResponseDto;
import com.oabs.patient_service.repository.PatientRepository;
import com.oabs.patient_service.service.PrescriptionClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.meta.When;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PrescriptionClient prescriptionClient;

    @Test
    void testRegisterPatient() {
        PatientRequestDto patientRequestDto = new PatientRequestDto("Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findByEmail(patientRequestDto.getEmail())).thenReturn(null);
        when(patientRepository.save(any(Patient.class))).thenReturn(new Patient());
        PatientResponseDto response = patientService.registerPatient(patientRequestDto);
        assertNotNull(response);
    }

    @Test
    void testGetPatientById() {
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        PatientDetailsDto result = patientService.getPatientById(patientId);
        assertNotNull(result);
        assertEquals(patient.getName(), result.getName());
    }

    @Test
    void testUpdatePatientById() {
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        PatientRequestDto patientRequestDto = new PatientRequestDto("Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        PatientResponseDto result = patientService.updatePatientById(patientId, patientRequestDto);
        assertNotNull(result);
        assertEquals(patient.getPatientId(),result.getPatientId());
    }

    @Test
    void testDeletePatientById() {
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        PatientResponseDto result = patientService.deletePatientById(patientId);
        assertNotNull(result);
        assertEquals("Patient Deleted successfully", result.getMessage());
    }

    @Test
    void testGetPatientByEmail() {
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(patient);
        PatientDetailsDto result = patientService.getPatientByEmail(patient.getEmail());
        assertNotNull(result);
        assertEquals(patient.getPatientId(), result.getPatientId());
    }

    @Test
    void testPatient_AlreadyExist() {
        PatientRequestDto patientRequestDto = new PatientRequestDto("Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findByEmail(patientRequestDto.getEmail())).thenReturn(patient);
        assertThrows(PatientAlreadyExistException.class, ()-> patientService.registerPatient(patientRequestDto));
    }

    @Test
    void testPatientById_NotFound() {
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        String email = "pashubasug@gmail.com";
        PatientRequestDto patientRequestDto = new PatientRequestDto("Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());
        assertThrows(PatientIdNotFoundException.class, ()->patientService.getPatientById(patientId));
        assertThrows(PatientIdNotFoundException.class, ()->patientService.deletePatientById(patientId));
        assertThrows(PatientIdNotFoundException.class, ()->patientService.updatePatientById(patientId,patientRequestDto));
        assertThrows(PatientIdNotFoundException.class,()->patientService.getPatientByEmail(email));
    }

}
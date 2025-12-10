package com.oabs.patient_service.service;

import com.oabs.patient_service.exception.PatientIdNotFoundException;
import com.oabs.patient_service.model.Patient;
import com.oabs.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientDetailsServiceTest {

    @InjectMocks
    private PatientDetailsService patientDetailsService;
    @Mock
    private PatientRepository patientRepository;

    @Test
    void testLoadUserByUsername_Found() {
        String username = "Basu";
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        Patient patient = new Patient(patientId,"Basu",
                "pashubasug@gmail.com","1234","9870654321", LocalDate.now());
        when(patientRepository.findByName(username)).thenReturn(Optional.of(patient));
        UserDetails result = patientDetailsService.loadUserByUsername(username);
        assertNotNull(result);
        assertEquals(patient.getName(), result.getUsername());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        String username = "Basu";
        when(patientRepository.findByName(username)).thenReturn(Optional.empty());
        assertThrows(PatientIdNotFoundException.class,()-> patientDetailsService.loadUserByUsername(username));
    }
}
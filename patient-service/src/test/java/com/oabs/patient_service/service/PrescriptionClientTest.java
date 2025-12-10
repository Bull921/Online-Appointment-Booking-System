package com.oabs.patient_service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrescriptionClientTest {

    @InjectMocks
    private PrescriptionClient prescriptionClient;
    @Mock
    private RestTemplate restTemplate;
    @Test
    void testDeletePrescriptionByPatient() {
        String patientId = "pId-"+ UUID.randomUUID().toString().substring(0,8);
        String url = "http://localhost:8083/api/prescriptions/patient/"+patientId;

        prescriptionClient.deletePrescriptionByPatient(patientId);
        verify(restTemplate,times(1)).delete(url);

    }
}
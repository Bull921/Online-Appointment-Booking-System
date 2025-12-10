package com.oabs.patient_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PrescriptionClient {

    @Autowired
    private RestTemplate restTemplate;

    public void deletePrescriptionByPatient(String patientId){
        String url = "http://localhost:8083/api/prescriptions/patient/"+patientId;
        restTemplate.delete(url);
    }
}


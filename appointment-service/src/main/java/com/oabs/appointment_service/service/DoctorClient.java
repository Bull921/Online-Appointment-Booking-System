package com.oabs.appointment_service.service;

import com.oabs.appointment_service.exception.AppoinrmentNotFoundException;
import com.oabs.appointment_service.model.AvailabilityDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class DoctorClient {

    private final RestTemplate restTemplate;

    public DoctorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AvailabilityDto getDoctorAvailability(String doctorId, LocalDate date, String slot){

        try{
            String url = "http://localhost:8081/api/doctors/"+doctorId+"/availability/"+date+"?availableSlots="+slot;
            return restTemplate.getForObject(url,AvailabilityDto.class);
        }catch (HttpClientErrorException.NotFound ex){
            throw new AppoinrmentNotFoundException("Error calling Doctor Service : Date and slot Unavailable");
        }
    }
}

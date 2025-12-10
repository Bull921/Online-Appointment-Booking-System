package com.oabs.doctor_service.service;

import com.oabs.doctor_service.model.*;

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {
    DoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto);
    Doctor getDoctorById(String doctorId);
    DoctorResponseDto updateDoctorById(String doctorId, DoctorRequestDto doctorRequestDto);
    DoctorResponseDto deleteDoctorById(String doctorId);
    List<Doctor> getAllDoctors();
    String addAvailability(String doctorId, AvailabilityDto availabilityDto);
    DoctorAvailabilityDto getAvailability(String doctorId);
    AvailabilityDto getAvailabilityByDate(String doctorId, LocalDate availableDate, String slot);
}

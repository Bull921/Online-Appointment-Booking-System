package com.oabs.doctor_service.service.impl;

import com.oabs.doctor_service.exception.DoctorAlreadyCreatedException;
import com.oabs.doctor_service.exception.DoctorDateAndSlotNotAvailableException;
import com.oabs.doctor_service.exception.DoctorNotFoundException;
import com.oabs.doctor_service.model.*;
import com.oabs.doctor_service.repository.DoctorAvailabilityRepository;
import com.oabs.doctor_service.repository.DoctorRepository;
import com.oabs.doctor_service.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public DoctorResponseDto addDoctor(DoctorRequestDto doctorRequestDto) {
        Doctor doctorExist = doctorRepository.findByEmail(doctorRequestDto.getEmail());
        if(doctorExist!=null){
            throw  new DoctorAlreadyCreatedException("Doctor Profile Already Created with Email : "
                    +doctorRequestDto.getEmail());
        }
        String doctorId = "Dr-"+ UUID.randomUUID().toString().substring(0,6);
        Doctor doctor = new Doctor(doctorId,doctorRequestDto.getName(),doctorRequestDto.getEmail(),
                doctorRequestDto.getSpecialization(),doctorRequestDto.getMobile());
        doctorRepository.save(doctor);
        return new DoctorResponseDto(doctorId, "Doctor Profile Created Successfully");
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor :"+ doctorId +" Not Found"));
    }

    @Override
    public DoctorResponseDto updateDoctorById(String doctorId, DoctorRequestDto doctorRequestDto) {
        Doctor doctor =doctorRepository.findById(doctorId)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor :"+ doctorId +" Not Found"));
        doctor.setName(doctorRequestDto.getName());
        doctor.setEmail(doctorRequestDto.getEmail());
        doctor.setSpecialization(doctorRequestDto.getSpecialization());
        doctor.setMobile(doctorRequestDto.getMobile());
        doctorRepository.save(doctor);
        return new DoctorResponseDto(doctorId,"Doctor details Updated Successfully");
    }

    @Override
    @Transactional
    public DoctorResponseDto deleteDoctorById(String doctorId) {
        Doctor doctor =doctorRepository.findById(doctorId)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor :"+ doctorId +" Not Found"));
        doctorAvailabilityRepository.deleteAllByDoctorId(doctorId);
        doctorRepository.deleteById(doctorId);
        return new DoctorResponseDto(doctorId,"Doctor Deleted Successfully");
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public String addAvailability(String doctorId, AvailabilityDto availabilityDto) {
        Doctor doctor =doctorRepository.findById(doctorId)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor :"+ doctorId +" Not Found"));
        DoctorAvailability doctorAvailability = new DoctorAvailability();
        doctorAvailability.setDoctorId(doctorId);
        doctorAvailability.setAvailableDate(availabilityDto.getAvailableDate());
        doctorAvailability.setAvailableSlots(availabilityDto.getAvailableSlots());
        doctorAvailabilityRepository.save(doctorAvailability);
        return "Doctor Availability Added successfully";
    }

    @Override
    public DoctorAvailabilityDto getAvailability(String doctorId) {
        Doctor doctor =doctorRepository.findById(doctorId)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor :"+ doctorId +" Not Found"));
        List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityRepository.findByDoctorId(doctorId);
        List<AvailabilityDto> availabilityDtoList = new ArrayList<>();
        for(DoctorAvailability doctorAvailability:doctorAvailabilityList){
            AvailabilityDto availabilityDto = new AvailabilityDto(doctorAvailability.getAvailableDate(),
                    doctorAvailability.getAvailableSlots());
            availabilityDtoList.add(availabilityDto);
        }
        return new DoctorAvailabilityDto(availabilityDtoList);
    }

    @Override
    public AvailabilityDto getAvailabilityByDate(String doctorId, LocalDate availableDate, String availableSlots) {
        Doctor doctor =doctorRepository.findById(doctorId)
                .orElseThrow(()-> new DoctorNotFoundException("Doctor :"+ doctorId +" Not Found"));
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository
                .findByAvailableDateAndAvailableSlots(availableDate, availableSlots);
        if(doctorAvailability==null){
            throw new DoctorDateAndSlotNotAvailableException("Date and Slot Not Available");
        }
        return new AvailabilityDto(doctorAvailability.getAvailableDate(),
                doctorAvailability.getAvailableSlots());
    }
}

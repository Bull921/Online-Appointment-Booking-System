package com.oabs.doctor_service.repository;

import com.oabs.doctor_service.model.AvailabilityDto;
import com.oabs.doctor_service.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctorId(String doctorId);

    DoctorAvailability findByAvailableDateAndAvailableSlots(LocalDate availableDate, String availableSlots);

    void deleteAllByDoctorId(String doctorId);
}

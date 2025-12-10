package com.oabs.doctor_service.repository;

import com.oabs.doctor_service.model.Doctor;
import com.oabs.doctor_service.service.DoctorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Doctor findByEmail(String email);
}

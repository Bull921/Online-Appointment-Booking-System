package com.oabs.patient_service.repository;

import com.oabs.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Patient findByEmail(String email);

    Optional<Patient> findByName(String username);
}

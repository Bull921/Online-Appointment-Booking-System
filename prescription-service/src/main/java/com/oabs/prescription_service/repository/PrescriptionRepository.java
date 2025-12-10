package com.oabs.prescription_service.repository;

import com.oabs.prescription_service.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    Prescription findByAppointmentId(String appointmentId);

    List<Prescription> findByPatientId(String patientId);

}

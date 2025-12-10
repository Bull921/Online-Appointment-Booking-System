package com.oabs.prescription_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {
    @Id
    @Column(name = "prescription_id", nullable = false, unique = true)
    private String prescriptionId;
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private String diagnosis;

    @ElementCollection
    @CollectionTable(
            name = "prescription_medicines",
            joinColumns = @JoinColumn(name = "prescription_id")
    )
    private List<Medicine> medicines;
    private String notes;
}

package com.oabs.prescription_service.service.impl;

import Doctor.DoctorResponse;
import com.oabs.prescription_service.exception.PrescriptionNotFoundException;
import com.oabs.prescription_service.grpc.DoctorGrpcClientService;
import com.oabs.prescription_service.model.*;
import com.oabs.prescription_service.repository.PrescriptionRepository;
import com.oabs.prescription_service.service.PrescriptionProducer;
import com.oabs.prescription_service.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionProducer prescriptionProducer;

    @Autowired
    private DoctorGrpcClientService doctorGrpcClientService;

    @Override
    public PrescriptionResponseDto addPrescription(PrescriptionRequestDto prescriptionRequestDto) {

        DoctorResponse doctorName = doctorGrpcClientService.getDoctorDetails(prescriptionRequestDto.getDoctorId());
        Prescription prescription = prescriptionRepository.
                findByAppointmentId(prescriptionRequestDto.getAppointmentId());
        if(prescription!=null){
            return new PrescriptionResponseDto("NA","Prescription Already available for Appoint : "+
                    prescriptionRequestDto.getAppointmentId());
        }
        long count = prescriptionRepository.count() + 1;
        String prescriptionId = String.format("Prsc-%04d",count);
        Prescription addPrescription = getPrescription(prescriptionRequestDto, prescriptionId);
        prescriptionRepository.save(addPrescription);

        PrescriptionEvent event = new PrescriptionEvent(doctorName.getName()+
                " has added a new prescription for you. Please check the portal.http://localhost:8083/api/prescriptions/appointment/"+
                prescriptionRequestDto.getAppointmentId());
        prescriptionProducer.sendMessage(event);

        return new PrescriptionResponseDto(prescriptionId,"Prescription added successfully");
    }

    @Override
    public PrescriptionDetailsDto getPrescriptionById(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(()-> new PrescriptionNotFoundException("Prescription : "+prescriptionId+" Not Found"));
        return getPrescriptionDetailsDto(prescription);
    }

    @Override
    public PrescriptionDetailsDto getPrescriptionByAppointmentId(String appointmentId) {
        Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId);
        if(prescription==null){
            throw new PrescriptionNotFoundException("Prescription Not Found for Appointment : "+appointmentId);
        }
        return getPrescriptionDetailsDto(prescription);
    }

    @Override
    public List<PrescriptionDetailsDto> getPrescriptionByPatientId(String patientId) {
        List<Prescription> prescriptionList = prescriptionRepository.findByPatientId(patientId);
        if(prescriptionList==null){
            throw new PrescriptionNotFoundException("Prescription Not Found for Patient : "+patientId);
        }
        List<PrescriptionDetailsDto> prescriptionDetailsDtoList = new ArrayList<>();
        for(Prescription prescription : prescriptionList){
            PrescriptionDetailsDto prescriptionDetailsDto = getPrescriptionDetailsDto(prescription);
            prescriptionDetailsDtoList.add(prescriptionDetailsDto);
        }
        return prescriptionDetailsDtoList;
    }

    @Override
    public String deletePrescriptionByPatientId(String patientId) {
        List<Prescription> prescriptionList = prescriptionRepository.findByPatientId(patientId);
        for(Prescription prescription : prescriptionList){
            prescriptionRepository.deleteById(prescription.getPrescriptionId());
        }
        return "Prescription added successfully";
    }

    private static Prescription getPrescription(PrescriptionRequestDto prescriptionRequestDto, String prescriptionId) {
        List<Medicine> medicineList = new ArrayList<>();
        for(MedicineDto dto : prescriptionRequestDto.getMedicines()){
            Medicine medicine = new Medicine(dto.getName(), dto.getDosage(), dto.getFrequency());
            medicineList.add(medicine);
        }
        return new Prescription(prescriptionId, prescriptionRequestDto.getAppointmentId(),
                prescriptionRequestDto.getDoctorId(), prescriptionRequestDto.getPatientId(), prescriptionRequestDto.getDiagnosis(),
                medicineList, prescriptionRequestDto.getNotes());
    }

    private static PrescriptionDetailsDto getPrescriptionDetailsDto(Prescription prescription){
        List<MedicineDto> medicineDtoList = new ArrayList<>();
        for(Medicine medicine : prescription.getMedicines()){
            MedicineDto medicineDto = new MedicineDto(medicine.getName(),medicine.getDosage(), medicine.getFrequency());
            medicineDtoList.add(medicineDto);
        }
        return new PrescriptionDetailsDto(prescription.getPrescriptionId(),prescription.getAppointmentId(),
                prescription.getDoctorId(),prescription.getPatientId(),prescription.getDiagnosis(),
                medicineDtoList,prescription.getNotes());
    }
}

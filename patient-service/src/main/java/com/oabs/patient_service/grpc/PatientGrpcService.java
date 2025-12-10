package com.oabs.patient_service.grpc;

import com.oabs.patient_service.exception.PatientIdNotFoundException;
import com.oabs.patient_service.model.Patient;
import com.oabs.patient_service.repository.PatientRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import patient.PatientRequest;
import patient.PatientResponse;
import patient.PatientServiceGrpc;

@GrpcService
public class PatientGrpcService extends PatientServiceGrpc.PatientServiceImplBase {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void getPatientDetails(PatientRequest request, StreamObserver<PatientResponse> responseObserver) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(()-> new PatientIdNotFoundException("Patient Id : "+request.getPatientId()+" Not Found"));
        PatientResponse patientResponse = PatientResponse.newBuilder()
                .setName(patient.getName())
                .build();
        responseObserver.onNext(patientResponse);
        responseObserver.onCompleted();
    }
}

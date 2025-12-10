package com.oabs.appointment_service.grpc;

import com.oabs.appointment_service.exception.AppoinrmentNotFoundException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import patient.PatientRequest;
import patient.PatientResponse;
import patient.PatientServiceGrpc;

@Service
public class PatientGrpcClientService {

    @GrpcClient("patientService")
    private PatientServiceGrpc.PatientServiceBlockingStub patientServiceBlockingStub;

    public PatientResponse getPatientDetails(String patientId){

        try{
            PatientRequest request = PatientRequest.newBuilder()
                    .setPatientId(patientId)
                    .build();
            return patientServiceBlockingStub.getPatientDetails(request);
        }catch (StatusRuntimeException ex){
            throw new AppoinrmentNotFoundException("Error calling Patient Service : Patient Id Not Found");
        }
    }
}

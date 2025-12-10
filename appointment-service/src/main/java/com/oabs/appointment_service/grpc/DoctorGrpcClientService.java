package com.oabs.appointment_service.grpc;

import Doctor.DoctorRequest;
import Doctor.DoctorResponse;
import Doctor.DoctorServiceGrpc;
import com.oabs.appointment_service.exception.AppoinrmentNotFoundException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class DoctorGrpcClientService {

    @GrpcClient("DoctorService")
    private DoctorServiceGrpc.DoctorServiceBlockingStub doctorServiceBlockingStub;

    public DoctorResponse getDoctorDetails(String doctorId){

        try{
            DoctorRequest request = DoctorRequest.newBuilder()
                    .setDoctorId(doctorId)
                    .build();
            return doctorServiceBlockingStub.getDoctorDetails(request);
        }catch(StatusRuntimeException ex){
            throw new AppoinrmentNotFoundException("Error calling Doctor Service : Doctor Id Not Found");
        }
    }
}

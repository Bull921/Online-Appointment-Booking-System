package com.oabs.doctor_service.grpc;

import Doctor.DoctorRequest;
import Doctor.DoctorResponse;
import Doctor.DoctorServiceGrpc;
import com.oabs.doctor_service.exception.DoctorNotFoundException;
import com.oabs.doctor_service.model.Doctor;
import com.oabs.doctor_service.repository.DoctorRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class DoctorGrpcService extends DoctorServiceGrpc.DoctorServiceImplBase {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void getDoctorDetails(DoctorRequest request, StreamObserver<DoctorResponse> responseObserver) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(()-> new DoctorNotFoundException("Doctor : "+request.getDoctorId()+" Not Found."));
        DoctorResponse doctorResponse = DoctorResponse.newBuilder()
                .setName(doctor.getName())
                .build();

        responseObserver.onNext(doctorResponse);
        responseObserver.onCompleted();
    }
}

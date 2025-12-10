package com.oabs.appointment_service.service;

import Doctor.DoctorResponse;
import com.oabs.appointment_service.exception.AppoinrmentNotFoundException;
import com.oabs.appointment_service.grpc.DoctorGrpcClientService;
import com.oabs.appointment_service.grpc.PatientGrpcClientService;
import com.oabs.appointment_service.model.*;
import com.oabs.appointment_service.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patient.PatientResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private AppointmentProducer appointmentProducer;

    @Autowired
    private DoctorGrpcClientService doctorGrpcClientService;
    @Autowired
    private PatientGrpcClientService patientGrpcClientService;

    public AppointmentResponseDto bookAppointment(AppointmentRequestDto appointmentRequestDto){

        DoctorResponse doctorName = doctorGrpcClientService.getDoctorDetails(appointmentRequestDto.getDoctorId());
        PatientResponse patientName = patientGrpcClientService.getPatientDetails(appointmentRequestDto.getPatientId());

        AvailabilityDto availabilityDto = doctorClient.getDoctorAvailability(appointmentRequestDto.getDoctorId(),
                appointmentRequestDto.getAppointmentDate(),appointmentRequestDto.getAvailableSlots());
        if(availabilityDto!=null){
            String appointmentId = "apt-"+UUID.randomUUID().toString().substring(0,8);
            Appointment appointment = new Appointment(appointmentId,appointmentRequestDto.getPatientId(),
                    appointmentRequestDto.getDoctorId(),appointmentRequestDto.getAppointmentDate(),
                    appointmentRequestDto.getAvailableSlots());
            appointmentRepository.save(appointment);

            AppointmentEvent appointmentEvent = new AppointmentEvent(patientName.getName()
                    +" appointment is confirmed with " +doctorName.getName()+" on "+appointment.getAppointmentDate()
                    +" at "+appointment.getAvailableSlots()+".");
            appointmentProducer.sendMessage(appointmentEvent);

            return new AppointmentResponseDto(appointmentId,"Booked","Appointment booked successfully");
        }else{
            return new AppointmentResponseDto("NA","Canceled","Date and Slot Not Available");
        }
    }

    public AppointDetailsDto getByAppointmentId(String appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new AppoinrmentNotFoundException("AppointmentId : "+appointmentId+" Not Found"));
        return new AppointDetailsDto(appointment.getAppointmentId(),appointment.getPatientId(),appointment.getDoctorId(),
                appointment.getAppointmentDate(),appointment.getAvailableSlots());
    }

    public List<Appointment> getByPatientId(String patientId){
        List<Appointment> appointment = appointmentRepository.findByPatientId(patientId);
        if(appointment==null){
            throw new AppoinrmentNotFoundException("PatientId : "+patientId+" Not Found");
        }
        return appointment;
    }

    public List<AppointDetailsDto> getByDoctorId(String doctorId){
        List<Appointment> appointment = appointmentRepository.findByDoctorId(doctorId);
        if(appointment==null){
            throw new AppoinrmentNotFoundException("DoctorId : "+doctorId+" Not Found");
        }
        List<AppointDetailsDto> appointDetailsDtoList = new ArrayList<>();
        for(Appointment appointment1: appointment){
            AppointDetailsDto appointDetailsDto = new AppointDetailsDto(appointment1.getAppointmentId(),appointment1.getPatientId(),appointment1.getDoctorId(),
                    appointment1.getAppointmentDate(),appointment1.getAvailableSlots());
            appointDetailsDtoList.add(appointDetailsDto);
        }
        return appointDetailsDtoList;
    }

    public String deleteByAppointmentId(String appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new AppoinrmentNotFoundException("AppointmentId : "+appointmentId+" Not Found"));

        DoctorResponse doctorName = doctorGrpcClientService.getDoctorDetails(appointment.getDoctorId());
        PatientResponse patientName = patientGrpcClientService.getPatientDetails(appointment.getPatientId());

        AppointmentEvent appointmentEvent = new AppointmentEvent(patientName.getName()
                +" appointment is canceled with " +doctorName.getName()+" on "+appointment.getAppointmentDate()
                +" at "+appointment.getAvailableSlots()+".");
        appointmentProducer.sendMessage(appointmentEvent);

        appointmentRepository.deleteById(appointmentId);
        return "Appointment canceled successfully";
    }

    public AppointmentResponseDto updateByAppointmentId(String appointmentId,
                                                        AppointmentRequestDto appointmentRequestDto){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new AppoinrmentNotFoundException("AppointmentId : "+appointmentId+" Not Found"));
        appointment.setPatientId(appointmentRequestDto.getPatientId());
        appointment.setDoctorId(appointmentRequestDto.getDoctorId());
        appointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
        appointment.setAvailableSlots(appointmentRequestDto.getAvailableSlots());
        appointmentRepository.save(appointment);

        DoctorResponse doctorName = doctorGrpcClientService.getDoctorDetails(appointment.getDoctorId());
        PatientResponse patientName = patientGrpcClientService.getPatientDetails(appointment.getPatientId());

        AppointmentEvent appointmentEvent = new AppointmentEvent(patientName.getName()
                +" appointment is rescheduled with " +doctorName.getName()+" on "+appointment.getAppointmentDate()
                +" at "+appointment.getAvailableSlots()+".");
        appointmentProducer.sendMessage(appointmentEvent);

        return new AppointmentResponseDto(appointmentId,"Rescheduled",
                "Appointment rescheduled successfully");
    }
}

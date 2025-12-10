package com.oabs.appointment_service.service;

import Doctor.DoctorResponse;
import com.oabs.appointment_service.grpc.DoctorGrpcClientService;
import com.oabs.appointment_service.grpc.PatientGrpcClientService;
import com.oabs.appointment_service.model.Appointment;
import com.oabs.appointment_service.model.AppointmentRequestDto;
import com.oabs.appointment_service.model.AppointmentResponseDto;
import com.oabs.appointment_service.model.AvailabilityDto;
import com.oabs.appointment_service.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import patient.PatientResponse;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private DoctorClient doctorClient;
    @Mock
    private AppointmentProducer appointmentProducer;
    @Mock
    private DoctorGrpcClientService doctorGrpcClientService;
    @Mock
    private PatientGrpcClientService patientGrpcClientService;


    @Test
    void testBookAppointment() {
        String appointmentId = "apt-"+UUID.randomUUID().toString().substring(0,8);
        AppointmentRequestDto appointmentRequestDto = new AppointmentRequestDto("pId-a790d8b0","Dr-dd8497", LocalDate.now(),
                "05:00-11:30");
        Appointment appointment = new Appointment(appointmentId,"pId-a790d8b0","Dr-dd8497", LocalDate.now(),
                "05:00-11:30");
        AvailabilityDto availabilityDto = new AvailabilityDto(LocalDate.now(), "05:00-11:30");
        DoctorResponse doctorName = DoctorResponse.newBuilder().setName("Dr.Smith").build();
        PatientResponse patientName = PatientResponse.newBuilder().setName("Basu").build();

        when(doctorGrpcClientService.getDoctorDetails("Dr-dd8497")).thenReturn(doctorName);
        when(patientGrpcClientService.getPatientDetails("pId-a790d8b0")).thenReturn(patientName);
        when(doctorClient.getDoctorAvailability("Dr-dd8497",availabilityDto.getAvailableDate(),availabilityDto.getAvailableSlots()))
                .thenReturn(availabilityDto);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        AppointmentResponseDto result = appointmentService.bookAppointment(appointmentRequestDto);
        assertNotNull(result);
        assertEquals("Booked",result.getStatus());

    }
    @Test
    void testBookAppointment_NoAvailability() {
        String appointmentId = "apt-"+UUID.randomUUID().toString().substring(0,8);
        AppointmentRequestDto appointmentRequestDto = new AppointmentRequestDto("pId-a790d8b0","Dr-dd8497", LocalDate.now(),
                "05:00-11:30");
        AvailabilityDto availabilityDto = new AvailabilityDto(LocalDate.now(), "05:00-11:30");
        when(doctorClient.getDoctorAvailability("Dr-dd8497",availabilityDto.getAvailableDate(),availabilityDto.getAvailableSlots()))
                .thenReturn(null);
        AppointmentResponseDto result = appointmentService.bookAppointment(appointmentRequestDto);
        assertNotNull(result);
        assertEquals("Canceled",result.getStatus());

    }
}
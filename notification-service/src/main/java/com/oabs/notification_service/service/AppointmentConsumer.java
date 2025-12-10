package com.oabs.notification_service.service;

import com.oabs.appointment_service.model.AppointmentEvent;
import com.oabs.prescription_service.model.PrescriptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppointmentConsumer {

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeAppointment(AppointmentEvent event){
        log.info(event.getMessage());
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.prescription.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumePrescription(PrescriptionEvent event){
        log.info(event.getMessage());
    }
}

package com.oabs.appointment_service.service;

import com.oabs.appointment_service.model.AppointmentEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducer {

    @Autowired
    private NewTopic newTopic;
    @Autowired
    private KafkaTemplate<String, AppointmentEvent> kafkaTemplate;

    public void sendMessage(AppointmentEvent appointmentEvent){
        Message<AppointmentEvent> message = MessageBuilder
                .withPayload(appointmentEvent)
                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
                .build();
        kafkaTemplate.send(message);
    }
}

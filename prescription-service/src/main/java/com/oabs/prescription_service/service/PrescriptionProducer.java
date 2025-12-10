package com.oabs.prescription_service.service;

import com.oabs.prescription_service.model.PrescriptionEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionProducer {

    @Autowired
    private NewTopic newTopic;

    @Autowired
    private KafkaTemplate<String, PrescriptionEvent> kafkaTemplate;

    public void sendMessage(PrescriptionEvent prescriptionEvent){
        Message<PrescriptionEvent> message = MessageBuilder
                .withPayload(prescriptionEvent)
                .setHeader(KafkaHeaders.TOPIC,newTopic.name())
                .build();
        kafkaTemplate.send(message);
    }
}

package com.kaiqkt.eventplatform.resources.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.domain.gateways.MessagingService;
import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.resources.exception.UnexpectedResourceException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagingServiceImpl implements MessagingService {

    @Value("${event-topic}")
    private String topic;

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper mapper;

    @Autowired
    public MessagingServiceImpl(KafkaTemplate<String, String> template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }
    @Override
    public void send(Event event) throws Exception {
        try {
            String data = mapper.writeValueAsString(event);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, event.getId(), data);
            template.send(record);
        } catch (Exception ex) {
            throw new UnexpectedResourceException(String.format("Error trying to send event to kafka, ex: %s", ex.toString()));
        }
    }
}

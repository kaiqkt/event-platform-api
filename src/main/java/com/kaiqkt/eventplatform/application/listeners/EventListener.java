package com.kaiqkt.eventplatform.application.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.domain.services.EventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListener {

    private final ObjectMapper mapper;
    private final EventService eventService;

    @Autowired
    public EventListener(ObjectMapper mapper, EventService eventService) {
        this.mapper = mapper;
        this.eventService = eventService;
    }

    @KafkaListener(topics = "event_platform", groupId = "ep-1")
    public void listener(ConsumerRecord<String, String> message, Acknowledgment ack) {
        try {
            Event event = mapper.readValue(message.value(), Event.class);
            eventService.consume(event);
            ack.acknowledge();
        } catch (Exception ex) {
            log.error("Fail to consume event: {}, error: {}", message.value(), ex.toString());
        }
    }
}

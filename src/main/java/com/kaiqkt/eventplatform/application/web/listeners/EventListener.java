package com.kaiqkt.eventplatform.application.web.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.application.config.ObjectMapperConfig;
import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.domain.services.ConsumerService;
import com.kaiqkt.eventplatform.domain.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final ObjectMapper mapper;
    private final ConsumerService consumerService;
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    public EventListener(ConsumerService consumerService) {
        this.mapper = ObjectMapperConfig.mapper();
        this.consumerService = consumerService;
    }

    @JmsListener(destination = "event-platform")
    public void listener(String message) throws Exception {
        try {
            Event event = mapper.readValue(message, Event.class);
            consumerService.consume(event);
        } catch (Exception ex) {
            log.error("Fail to consume event: {}, error: {}", message, ex.toString());
        }
    }
}

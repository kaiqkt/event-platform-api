package com.kaiqkt.eventplatform.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.gateways.MessagingService;
import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.domain.models.Version;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventService {

    @Value("${aws.queue-name}")
    private String queueName;
    private final ProducerService producerService;
    private final MessagingService messagingService;
    private final ObjectMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(EventService.class);


    @Autowired
    public EventService(ProducerService producerService, MessagingService messagingService, ObjectMapper mapper) {
        this.producerService = producerService;
        this.messagingService = messagingService;
        this.mapper = mapper;
    }

    public void send(Event event) throws Exception {
        Version version = producerService.findVersion(event.getService(), event.getAction(), event.getVersion());

        if (!validate(event.getData(), version.getSchema())) {
            throw new DomainException(ErrorType.INVALID_EVENT);
        }

        messagingService.send(event, queueName);

        log.info("Event {} sent successfully", event.getId());
    }

    private boolean validate(Map<String, Object> data, String schema) throws JsonProcessingException {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema jsonSchema = factory.getSchema(schema);
        JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(data));

        return jsonSchema.validate(jsonNode).isEmpty();
    }
}

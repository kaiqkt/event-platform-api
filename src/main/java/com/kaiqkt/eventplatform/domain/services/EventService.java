package com.kaiqkt.eventplatform.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.gateways.MessagingService;
import com.kaiqkt.eventplatform.domain.gateways.RequestService;
import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.models.Version;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EventService {

    private final ProducerService producerService;
    private final ConsumerService consumerService;
    private final MessagingService messagingService;
    private final RequestService requestService;
    private final ObjectMapper mapper;


    @Autowired
    public EventService(ProducerService producerService, ConsumerService consumerService, MessagingService messagingService, RequestService requestService, ObjectMapper mapper) {
        this.producerService = producerService;
        this.consumerService = consumerService;
        this.messagingService = messagingService;
        this.requestService = requestService;
        this.mapper = mapper;
    }

    public void send(Event event) throws Exception {
        Producer producer = producerService.find(event.getService(), event.getAction(), event.getVersion());
        Version version = producer.findVersionByValue(event.getVersion());

        if (!validate(event.getData(), version.getSchema())) {
            throw new DomainException(ErrorType.INVALID_EVENT.setMessage(version.getSchema()));
        }

        messagingService.send(event);

        log.info("Event for service {} sent successfully", event.getService());
    }

    public void consume(Event event) {
        consumerService.find(event.getService(), event.getAction(), event.getVersion())
                .forEach(consumer -> CompletableFuture.runAsync(() -> {
                    try {
                        requestService.make(consumer.getUrl(), consumer.getContentType(), event.getData());
                        log.info("Event {} consumed by service {} successfully", event.getId(), consumer.getService());
                    } catch (Exception e) {
                        log.error("Fail to make request for event: {} on consumer {}, error: {}", event, consumer.getService(), e.toString());
                    }
                }));
    }

    private boolean validate(Map<String, Object> data, String schema) throws JsonProcessingException {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema jsonSchema = factory.getSchema(schema);
        JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(data));

        return jsonSchema.validate(jsonNode).isEmpty();
    }
}

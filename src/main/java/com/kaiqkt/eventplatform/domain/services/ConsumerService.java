package com.kaiqkt.eventplatform.domain.services;

import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.gateways.RequestService;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.models.Version;
import com.kaiqkt.eventplatform.domain.repositories.ConsumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ConsumerService {

    private final ProducerService producerService;
    private final ConsumerRepository consumerRepository;
    private final RequestService requestService;
    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    public ConsumerService(
            ProducerService producerService,
            ConsumerRepository consumerRepository,
            RequestService requestService
    ) {
        this.producerService = producerService;
        this.consumerRepository = consumerRepository;
        this.requestService = requestService;
    }

    public Consumer create(
            Consumer consumer,
            String service,
            String action,
            Integer versionValue
    ) throws DomainException {
        if (consumerRepository.exists(consumer.getService(), service, action, versionValue)) {
            throw new DomainException(ErrorType.CONSUMER_ALREADY_EXISTS);
        }

        Producer producer = producerService.find(service, action, versionValue);
        Version version = producer.getVersions().stream()
                .filter(v -> v.getValue().equals(versionValue))
                .findFirst()
                .orElseThrow(() -> new DomainException(ErrorType.VERSION_NOT_FOUND));

        consumer.setVersion(version);
        consumerRepository.save(consumer);

        log.info("Consumer {} from producer {} created successfully", consumer.getService(), service);

        return consumer;
    }

    public void consume(Event event) {
        log.info("Consuming event {}", event.getId());

        List<CompletableFuture<Void>> futures = consumerRepository.findAll(
                        event.getService(),
                        event.getAction(),
                        event.getVersion())
                .stream()
                .map(consumer -> CompletableFuture.runAsync(() -> processConsumer(consumer, event)))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.info("Event {} consumed successfully", event.getId());
    }

    public Page<Consumer> findAll(String service, PageRequest pageRequest) {
        return consumerRepository.findAll(service, pageRequest);
    }

    private void processConsumer(Consumer consumer, Event event) {
        try {
            requestService.request(consumer.getUrl(), consumer.getContentType(), event.getData());
        } catch (Exception e) {
            log.error("Service {} failed to consume event: {}, error: {}", consumer.getService(), event, e.toString());
        }
    }
}

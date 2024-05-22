package com.kaiqkt.eventplatform.domain.services;

import com.kaiqkt.eventplatform.application.config.Metrics;
import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.models.Version;
import com.kaiqkt.eventplatform.domain.repositories.ConsumerRepository;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConsumerService {

    private final ProducerService producerService;
    private final ConsumerRepository consumerRepository;
    private final Metrics metrics;

    @Autowired
    public ConsumerService(ProducerService producerService, ConsumerRepository consumerRepository, Metrics metrics) {
        this.producerService = producerService;
        this.consumerRepository = consumerRepository;
        this.metrics = metrics;
    }

    public Consumer create(Consumer consumer, String service, String action, Integer versionValue) throws DomainException {
        if (consumerRepository.exists(consumer.getService(),service, action, versionValue)) {
            throw new DomainException(ErrorType.CONSUMER_ALREADY_EXISTS);
        }

        Producer producer = producerService.find(service, action, versionValue);
        Version version = producer.findVersionByValue(versionValue);

        consumer.setVersion(version);
        consumerRepository.save(consumer);

        log.info("Consumer for service {} to service {} created successfully", consumer.getService(), service);
        metrics.increment("consumer", "action", "create");

        return consumer;
    }

    //deletar consumer

//    @Timed(value = "consumer", extraTags = {"action", "find"})
    public List<Consumer> find(String service, String action, Integer version) throws Exception {
        return metrics.timer("consumer", () -> consumerRepository.find(service, action, version), "action", "find");
    }

    public List<Consumer> findByService(String service) throws DomainException {
        return Optional.ofNullable(consumerRepository.findAllByService(service))
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new DomainException(ErrorType.CONSUMER_NOT_FOUND));
    }
}

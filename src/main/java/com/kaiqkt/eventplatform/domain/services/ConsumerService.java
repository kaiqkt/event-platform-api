package com.kaiqkt.eventplatform.domain.services;

import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.models.Version;
import com.kaiqkt.eventplatform.domain.repositories.ConsumerRepository;
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

    @Autowired
    public ConsumerService(ProducerService producerService, ConsumerRepository consumerRepository) {
        this.producerService = producerService;
        this.consumerRepository = consumerRepository;
    }

    public Consumer create(Consumer consumer, String service, String action, Integer versionValue) throws DomainException {
        Producer producer = producerService.find(service, action, versionValue);
        Version version = producer.findVersionByValue(versionValue);

        consumer.setVersion(version);
        consumerRepository.save(consumer);

        log.info("Consumer for service {} to service {} persisted successfully", consumer.getService(), service);

        return consumer;
    }

    //deletar consumer

    public List<Consumer> find(String service, String action, Integer version) {
        return consumerRepository.find(service, action, version);
    }

    public List<Consumer> findByService(String service) throws DomainException {
        return Optional.ofNullable(consumerRepository.findAllByService(service))
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new DomainException(ErrorType.CONSUMER_NOT_FOUND));
    }
}

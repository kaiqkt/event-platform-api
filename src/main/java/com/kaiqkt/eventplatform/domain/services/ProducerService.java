package com.kaiqkt.eventplatform.domain.services;

import com.kaiqkt.eventplatform.application.config.Metrics;
import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.repositories.ProducerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final Metrics metrics;

    @Autowired
    public ProducerService(ProducerRepository producerRepository, Metrics metrics) {
        this.producerRepository = producerRepository;
        this.metrics = metrics;
    }

    public Producer upsert(Producer producer) throws DomainException {
        if (!producer.isFirstVersionValid()) {
            throw new DomainException(ErrorType.VERSION_SHOULD_BE_SEQUENTIAL);
        }

        Optional<Producer> existingProducer = producerRepository.findByServiceAndAction(producer.getService(), producer.getAction());

        if (existingProducer.isPresent()) {
            return update(producer, existingProducer.get());
        } else {
            return create(producer);
        }
    }

    public void deleteVersion(String service, String action, Integer version) {
        Optional<Producer> existingProducer = producerRepository.find(service, action, version);
        if (existingProducer.isPresent()) {
            existingProducer.get().removeVersion(version);
            existingProducer.get().setUpdatedAt(LocalDateTime.now());

            producerRepository.save(existingProducer.get());

            log.info("Producer version {} for service {} deleted successfully", version, service);
        }
    }

    public Producer findByService(String service) throws DomainException {;
        return producerRepository.findByService(service)
                .orElseThrow(() -> new DomainException(ErrorType.PRODUCER_NOT_FOUND));
    }

    public Producer findByServiceAndAction(String service, String action) throws DomainException {
        return producerRepository.findByServiceAndAction(service, action).orElseThrow(() -> new DomainException(ErrorType.PRODUCER_NOT_FOUND));
    }

    public Producer find(String service, String action, Integer version) throws DomainException {
        return producerRepository.find(service, action, version).orElseThrow(() -> new DomainException(ErrorType.PRODUCER_NOT_FOUND));
    }

    private Producer update(Producer producer, Producer existingProducer) throws DomainException {
        if (existingProducer.hasVersion(producer.getFirstVersion())) {
            return existingProducer;
        }

        producer.getFirstVersion().setProducer(existingProducer);
        existingProducer.setVersion(producer.getFirstVersion());
        existingProducer.setUpdatedAt(LocalDateTime.now());

        if (!existingProducer.isVersionSequential(producer.getFirstVersion())) {
            throw new DomainException(ErrorType.VERSION_SHOULD_BE_SEQUENTIAL);
        }

        Producer updatedProducer = producerRepository.save(existingProducer);

        log.info("Producer for service {} updated with new version", producer.getService());
        metrics.increment("producer", "action", "new_version");

        return updatedProducer;
    }

    private Producer create(Producer producer) {
        Producer newProducer = producerRepository.save(producer);

        log.info("Producer for service {} created", producer.getService());
        metrics.increment("producer", "action", "created");

        return newProducer;
    }
}

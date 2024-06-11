package com.kaiqkt.eventplatform.domain.services;

import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.models.Version;
import com.kaiqkt.eventplatform.domain.repositories.ProducerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProducerService {

    private static final Integer FIRST_VERSION = 1;
    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);

    private final ProducerRepository producerRepository;

    @Autowired
    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Producer createOrUpdate(String service, String action, Version version) throws DomainException {
        Optional<Producer> producer = producerRepository.findByServiceAndAction(service, action);

        if (producer.isPresent()) {
            return update(producer.get(), version);
        } else {
            return create(service, action, version);
        }
    }

    public void deleteVersion(String service, String action, Integer version) throws DomainException {
        Producer producer = producerRepository.findByServiceAndAction(service, action)
                .orElseThrow(() -> new DomainException(ErrorType.PRODUCER_NOT_FOUND));
        producer.getVersions().removeIf(v -> v.getValue().equals(version));
        producerRepository.save(producer);
    }

    public Page<Producer> findAll(String service, String action, PageRequest pageable) {
        return producerRepository.findAll(service, action, pageable);
    }

    public Producer findByServiceAndAction(String service, String action) throws DomainException {
        return producerRepository.findByServiceAndAction(service, action).orElseThrow(() -> new DomainException(ErrorType.PRODUCER_NOT_FOUND));
    }

    public Version findVersion(String service, String action, Integer version) throws DomainException {
        return findByServiceAndAction(service, action).getVersions().stream()
                .filter(v -> v.getValue().equals(version))
                .findFirst()
                .orElseThrow(() -> new DomainException(ErrorType.VERSION_NOT_FOUND));
    }

    public Producer find(String service, String action, Integer version) throws DomainException {
        return producerRepository.find(service, action, version).orElseThrow(() -> new DomainException(ErrorType.PRODUCER_NOT_FOUND));
    }

    private Producer update(Producer producer, Version version) throws DomainException {
        Integer lastVersionValue = producer.getVersions().stream().map(Version::getValue).max(Integer::compareTo).orElse(0);
        if (lastVersionValue + 1 != version.getValue()) {
            throw new DomainException(ErrorType.INVALID_VERSION.setMessage("The version must be sequential"));
        }

        producer.getVersions().add(version);
        version.setProducer(producer);
        producer.setUpdatedAt(LocalDateTime.now());

        producerRepository.save(producer);
        log.info("Producer service {} with action {} updated successfully", producer.getService(), producer.getAction());

        return producer;
    }

    private Producer create(String service, String action, Version version) throws DomainException {
        if (!Objects.equals(version.getValue(), FIRST_VERSION)) {
            throw new DomainException(ErrorType.INVALID_VERSION.setMessage("Version should start with 1"));
        }

        Producer producer = new Producer(service, action, version);
        version.setProducer(producer);
        producerRepository.save(producer);
        log.info("Producer service {} with action {} created successfully", producer.getService(), producer.getAction());

        return producer;
    }
}

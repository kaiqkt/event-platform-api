package com.kaiqkt.eventplatform.domain.services;

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

    @Autowired
    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Producer create(Producer producer) throws DomainException {
        Optional<Producer> existingProducer = producerRepository.findByServiceAndAction(producer.getService(), producer.getAction());

        if (existingProducer.isPresent()) {
            if (existingProducer.get().hasVersion(producer.getFirstVersion())) {
                return existingProducer.get();
            }

            producer.getFirstVersion().setProducer(existingProducer.get());
            existingProducer.get().setVersion(producer.getFirstVersion());
            existingProducer.get().setUpdatedAt(LocalDateTime.now());

            if (!existingProducer.get().isVersionSequential(producer.getFirstVersion())) {
                throw new DomainException(ErrorType.VERSION_SHOULD_BE_SEQUENTIAL);
            }

            log.info("Producer for service {} updated successfully", producer.getService());

            return producerRepository.save(existingProducer.get());
        }

        if (!producer.isFirstVersionValid()) {
            throw new DomainException(ErrorType.VERSION_SHOULD_BE_SEQUENTIAL);
        }

        log.info("Producer for service {} persisted successfully", producer.getService());

        return producerRepository.save(producer);
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
}

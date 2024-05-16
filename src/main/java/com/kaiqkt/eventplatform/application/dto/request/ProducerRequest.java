package com.kaiqkt.eventplatform.application.dto.request;

import com.kaiqkt.eventplatform.application.dto.VersionDTO;
import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerRequestV1;

public class ProducerRequest {
    public static Producer toDomain(ProducerRequestV1 request) throws DomainException {
        Producer producer = new Producer(request.getService(), request.getAction(), VersionDTO.toDomain(request.getVersion()));
        producer.getFirstVersion().setProducer(producer);
        return producer;
    }
}

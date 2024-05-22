package com.kaiqkt.eventplatform.application.controllers;

import com.kaiqkt.eventplatform.application.dto.request.ProducerRequest;
import com.kaiqkt.eventplatform.application.dto.response.ProducerResponse;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.services.ProducerService;
import com.kaiqkt.eventplatform.generated.application.controllers.ProducerApi;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerRequestV1;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProducerController implements ProducerApi {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    //role de admin
    @Override
    public ResponseEntity<ProducerResponseV1> create(ProducerRequestV1 producerRequestV1) throws Exception {
        Producer producer = producerService.upsert(ProducerRequest.toDomain(producerRequestV1));
        return ResponseEntity.ok(ProducerResponse.toV1(producer));
    }

    // role de admin
    @Override
    public ResponseEntity<Void> deleteVersion(String service, String action, Integer version) throws Exception {
        producerService.deleteVersion(service, action, version);
        return ResponseEntity.noContent().build();
    }

    //admin
    @Override
    public ResponseEntity<ProducerResponseV1> findByServiceAndAction(String service, String action) throws Exception {
        Producer producer = producerService.findByServiceAndAction(service, action);
        return ResponseEntity.ok(ProducerResponse.toV1(producer));
    }

    //admin
    @Override
    public ResponseEntity<ProducerResponseV1> findByService(String service) throws Exception {
        ProducerResponseV1 producer = ProducerResponse.toV1(producerService.findByService(service));
        return ResponseEntity.ok(producer);
    }
}

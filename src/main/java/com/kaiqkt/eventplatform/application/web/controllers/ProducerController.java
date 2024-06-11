package com.kaiqkt.eventplatform.application.web.controllers;

import com.kaiqkt.eventplatform.application.dto.VersionDto;
import com.kaiqkt.eventplatform.application.dto.response.PageResponse;
import com.kaiqkt.eventplatform.application.dto.response.ProducerResponse;
import com.kaiqkt.eventplatform.application.dto.request.PageRequest;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.domain.services.ProducerService;
import com.kaiqkt.eventplatform.generated.application.controllers.ProducerApi;
import com.kaiqkt.eventplatform.generated.application.dto.PageResponseV1;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerRequestV1;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController implements ProducerApi {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    //admin
    @Override
    public ResponseEntity<ProducerResponseV1> create(ProducerRequestV1 requestV1) throws Exception {
        Producer producer = producerService.createOrUpdate(requestV1.getService(), requestV1.getAction(), VersionDto.toDomain(requestV1.getVersion()));
        return ResponseEntity.ok(ProducerResponse.toV1(producer));
    }

    //admin
    @Override
    public ResponseEntity<Void> deleteVersion(String service, String action, Integer version) throws Exception {
        producerService.deleteVersion(service, action, version);
        return ResponseEntity.noContent().build();
    }

    //admin
    @Override
    public ResponseEntity<PageResponseV1> findAll(String service, String action, Integer page, Integer size, String order, String sortBy) throws Exception {
        Page<Producer> response = producerService.findAll(service, action, PageRequest.of(page, size, order, sortBy));
        return ResponseEntity.ok(PageResponse.toResponse(response, ProducerResponse::toV1));
    }
}

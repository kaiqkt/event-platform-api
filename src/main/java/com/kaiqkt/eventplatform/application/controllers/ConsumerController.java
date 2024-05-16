package com.kaiqkt.eventplatform.application.controllers;

import com.kaiqkt.eventplatform.application.dto.request.ConsumerRequest;
import com.kaiqkt.eventplatform.application.dto.response.ConsumerResponse;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.domain.services.ConsumerService;
import com.kaiqkt.eventplatform.generated.application.controllers.ConsumerApi;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerRequestV1;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConsumerController implements ConsumerApi {

    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    //admin
    @Override
    public ResponseEntity<ConsumerResponseV1> create(String service, String action, Integer version, ConsumerRequestV1 consumerRequestV1) throws Exception {
        Consumer consumer = consumerService.create(ConsumerRequest.toDomain(consumerRequestV1), service, action, version);
        return ResponseEntity.ok(ConsumerResponse.toResponse(consumer));
    }

    @Override
    public ResponseEntity<List<ConsumerResponseV1>> findByService(String service) throws Exception {
        List<ConsumerResponseV1> response = consumerService.findByService(service).stream().map(ConsumerResponse::toResponse).toList();
        return ResponseEntity.ok(response);
    }
}

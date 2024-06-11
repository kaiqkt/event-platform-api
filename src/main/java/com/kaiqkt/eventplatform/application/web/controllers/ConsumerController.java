package com.kaiqkt.eventplatform.application.web.controllers;

import com.kaiqkt.eventplatform.application.dto.request.ConsumerRequest;
import com.kaiqkt.eventplatform.application.dto.response.ConsumerResponse;
import com.kaiqkt.eventplatform.application.dto.response.PageResponse;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.domain.utils.PageRequestBuilder;
import com.kaiqkt.eventplatform.domain.services.ConsumerService;
import com.kaiqkt.eventplatform.generated.application.controllers.ConsumerApi;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerRequestV1;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerResponseV1;
import com.kaiqkt.eventplatform.generated.application.dto.PageResponseV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.kaiqkt.eventplatform.domain.utils.Constants.DEFAULT_SORT_PROPERTIES;

@RestController
public class ConsumerController implements ConsumerApi {

    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    //admin
    @Override
    public ResponseEntity<ConsumerResponseV1> create(
            String service, String action,
            Integer version,
            ConsumerRequestV1 consumerRequestV1
    ) throws Exception {
        Consumer consumer = consumerService
                .create(ConsumerRequest.toDomain(consumerRequestV1), service, action, version);
        return ResponseEntity.ok(ConsumerResponse.toV1(consumer));
    }

    //admin
    @Override
    public ResponseEntity<PageResponseV1> findAll(
            String service,
            Integer page,
            Integer size,
            String order,
            String sortBy
    ) {
        PageRequest pageRequest = PageRequestBuilder.build(page, size, order, sortBy, DEFAULT_SORT_PROPERTIES);
        Page<Consumer> response = consumerService.findAll(service, pageRequest);
        return ResponseEntity.ok(PageResponse.toResponse(response, ConsumerResponse::toV1));
    }
}

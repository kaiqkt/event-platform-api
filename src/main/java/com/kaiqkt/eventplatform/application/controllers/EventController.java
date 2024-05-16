package com.kaiqkt.eventplatform.application.controllers;

import com.kaiqkt.eventplatform.application.dto.request.EventRequest;
import com.kaiqkt.eventplatform.domain.services.EventService;
import com.kaiqkt.eventplatform.generated.application.controllers.EventApi;
import com.kaiqkt.eventplatform.generated.application.dto.EventV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController implements EventApi {


    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //role service
    @Override
    public ResponseEntity<Void> send(EventV1 eventV1) throws Exception {
        eventService.send(EventRequest.toDomain(eventV1));
        return ResponseEntity.accepted().build();
    }
}

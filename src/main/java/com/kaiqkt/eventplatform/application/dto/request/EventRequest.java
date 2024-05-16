package com.kaiqkt.eventplatform.application.dto.request;

import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.generated.application.dto.EventV1;
import io.azam.ulidj.ULID;

import java.util.Optional;

public class EventRequest {

    public static Event toDomain(EventV1 request) {
        return Event.builder()
                .id(Optional.ofNullable(request.getId()).orElse(ULID.random()))
                .action(request.getAction())
                .service(request.getService())
                .data(request.getData())
                .version(request.getVersion()).build();
    }
}

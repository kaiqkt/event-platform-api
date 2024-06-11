package com.kaiqkt.eventplatform.application.dto.request;

import com.kaiqkt.eventplatform.domain.models.Event;
import com.kaiqkt.eventplatform.generated.application.dto.EventV1;
import io.azam.ulidj.ULID;

import java.util.Optional;

public class EventRequest {

    public static Event toDomain(EventV1 request) {
        Event event = new Event();
        event.setId(Optional.ofNullable(request.getId()).orElse(ULID.random()));
        event.setAction(request.getAction());
        event.setService(request.getService());
        event.setData(request.getData());
        event.setVersion(request.getVersion());

        return event;
    }
}

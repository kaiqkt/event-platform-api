package com.kaiqkt.eventplatform.domain.gateways;

import com.kaiqkt.eventplatform.domain.models.Event;

public interface MessagingService {
    void send(Event event) throws Exception;
}

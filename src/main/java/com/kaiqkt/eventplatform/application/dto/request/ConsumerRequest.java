package com.kaiqkt.eventplatform.application.dto.request;

import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerRequestV1;

public class ConsumerRequest {
    public static Consumer toDomain(ConsumerRequestV1 requestV1) {
        Consumer consumer = new Consumer();
        consumer.setService(requestV1.getService());
        consumer.setUrl(requestV1.getUrl());
        consumer.setContentType(requestV1.getContentType());
        return consumer;
    }
}

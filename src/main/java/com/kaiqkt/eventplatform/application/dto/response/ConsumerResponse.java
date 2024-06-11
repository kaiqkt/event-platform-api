package com.kaiqkt.eventplatform.application.dto.response;

import com.kaiqkt.eventplatform.application.dto.VersionDto;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerResponseV1;

public class ConsumerResponse {
    public static ConsumerResponseV1 toV1(Consumer consumer) {
        ConsumerResponseV1 response = new ConsumerResponseV1();
        response.service(consumer.getService());
        response.url(consumer.getUrl());
        response.version(VersionDto.toV1(consumer.getVersion()));
        response.contentType(consumer.getContentType());
        return response;
    }
}

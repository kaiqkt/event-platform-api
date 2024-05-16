package com.kaiqkt.eventplatform.application.dto.response;

import com.kaiqkt.eventplatform.application.dto.VersionDTO;
import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerResponseV1;

public class ConsumerResponse {
    public static ConsumerResponseV1 toResponse(Consumer consumer) {
        return ConsumerResponseV1.builder()
                .id(consumer.getId())
                .service(consumer.getService())
                .url(consumer.getUrl())
                .version(VersionDTO.toV1(consumer.getVersion()))
                .contentType(consumer.getContentType())
                .build();
    }
}

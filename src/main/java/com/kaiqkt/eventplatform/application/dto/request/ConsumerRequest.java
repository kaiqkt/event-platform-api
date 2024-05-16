package com.kaiqkt.eventplatform.application.dto.request;

import com.kaiqkt.eventplatform.domain.models.Consumer;
import com.kaiqkt.eventplatform.generated.application.dto.ConsumerRequestV1;

public class ConsumerRequest {
    public static Consumer toDomain(ConsumerRequestV1 requestV1) {
        return new Consumer(requestV1.getService(), requestV1.getUrl(), requestV1.getContentType());
    }
}

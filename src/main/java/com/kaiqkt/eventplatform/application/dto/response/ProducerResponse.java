package com.kaiqkt.eventplatform.application.dto.response;

import com.kaiqkt.eventplatform.application.dto.VersionDTO;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerResponseV1;

public class ProducerResponse {
    public static ProducerResponseV1 toV1(Producer producer) {
        return ProducerResponseV1.builder()
                .service(producer.getService())
                .action(producer.getAction())
                .versions(producer.getVersions().stream().map(VersionDTO::toV1).toList())
                .build();
    }
}

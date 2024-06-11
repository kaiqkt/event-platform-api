package com.kaiqkt.eventplatform.application.dto.response;

import com.kaiqkt.eventplatform.application.dto.VersionDto;
import com.kaiqkt.eventplatform.domain.models.Producer;
import com.kaiqkt.eventplatform.generated.application.dto.ProducerResponseV1;

public class ProducerResponse {
    public static ProducerResponseV1 toV1(Producer producer) {
        ProducerResponseV1 responseV1 = new ProducerResponseV1();
        responseV1.setService(producer.getService());
        responseV1.setAction(producer.getAction());
        responseV1.setVersions(producer.getVersions().stream().map(VersionDto::toV1).toList());
        responseV1.setCreatedAt(producer.getCreatedAt().toString());
        responseV1.setUpdatedAt(producer.getUpdatedAt() != null ? producer.getUpdatedAt().toString() : null);
        return responseV1;
    }
}

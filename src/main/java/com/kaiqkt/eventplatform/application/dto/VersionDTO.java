package com.kaiqkt.eventplatform.application.dto;

import com.kaiqkt.eventplatform.domain.models.Version;
import com.kaiqkt.eventplatform.generated.application.dto.VersionV1;

public class VersionDTO {
    public static Version toDomain(VersionV1 request) {
        return new Version(request.getValue(), request.getSchema());
    }

    public static VersionV1 toV1(Version request) {
        return new VersionV1(request.getValue(), request.getSchema());
    }
}

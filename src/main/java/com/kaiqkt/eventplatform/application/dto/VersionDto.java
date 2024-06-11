package com.kaiqkt.eventplatform.application.dto;

import com.kaiqkt.eventplatform.domain.models.Version;
import com.kaiqkt.eventplatform.generated.application.dto.VersionV1;

public class VersionDto {
    public static Version toDomain(VersionV1 request) {
        Version version = new Version();
        version.setValue(request.getValue());
        version.setSchema(request.getSchema());
        return version;
    }

    public static VersionV1 toV1(Version request) {
        return new VersionV1(request.getValue(), request.getSchema());
    }
}

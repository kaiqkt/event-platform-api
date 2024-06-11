package com.kaiqkt.eventplatform.resources.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "aws")
@Validated
public record AWSProperties(
        String region,
        String accessKey,
        String secretKey,
        String endpoint
) {

}

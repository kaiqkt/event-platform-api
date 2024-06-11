package com.kaiqkt.eventplatform.resources.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.application.config.ObjectMapperConfig;
import com.kaiqkt.eventplatform.domain.gateways.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RequestServiceImpl implements RequestService {

    private final ObjectMapper mapper = ObjectMapperConfig.mapper();
    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Override
    public void request(String url, String contentType, Map<String, Object> data) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(contentType));
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(data), headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}

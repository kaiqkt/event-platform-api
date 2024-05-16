package com.kaiqkt.eventplatform.resources.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.domain.gateways.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Component
public class RequestServiceImpl implements RequestService {

    private final ObjectMapper mapper;

    @Autowired
    public RequestServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void make(String url, String contentType, Map<String, Object> data) throws Exception {
        RestClient restClient = RestClient.create(url);

        restClient.post()
                .contentType(MediaType.parseMediaType(contentType))
                .body(mapper.writeValueAsString(data))
                .retrieve()
                .toBodilessEntity();
    }
}

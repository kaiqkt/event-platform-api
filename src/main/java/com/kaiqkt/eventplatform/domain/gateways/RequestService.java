package com.kaiqkt.eventplatform.domain.gateways;

import java.util.Map;

public interface RequestService {
    void make(String url, String contentType, Map<String, Object> data) throws Exception;
}

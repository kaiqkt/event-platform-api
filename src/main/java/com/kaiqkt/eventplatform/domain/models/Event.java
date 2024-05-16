package com.kaiqkt.eventplatform.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String id;
    private String service;
    private String action;
    private Integer version;
    private Map<String, Object> data;
}

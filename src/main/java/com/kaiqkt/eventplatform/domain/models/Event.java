package com.kaiqkt.eventplatform.domain.models;

import java.util.Map;

public class Event {
    private String id;
    private String service;
    private String action;
    private Integer version;
    private Map<String, Object> data;

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", service='" + service + '\'' +
                ", action='" + action + '\'' +
                ", version=" + version +
                ", data=" + data +
                '}';
    }
}

package com.kaiqkt.eventplatform.domain.models;

import io.azam.ulidj.ULID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Producer {

    @Id
    private String id;
    private String service;
    private String action;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Version> versions;

    public Producer() { }

    public Producer(String service, String action, Version version) {
        this.id = ULID.random();
        this.service = service;
        this.action = action;
        this.versions = List.of(version);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "Producer { " +
                "id='" + id + '\'' +
                ", service='" + service + '\'' +
                ", action='" + action + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", versions=" + versions +
                " }";
    }
}

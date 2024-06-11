package com.kaiqkt.eventplatform.domain.models;

import io.azam.ulidj.ULID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Version {
    @Id
    private String id;
    private Integer value;
    private String schema;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer;

    public Version() {
        this.id = ULID.random();
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "Version{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", schema='" + schema + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.kaiqkt.eventplatform.domain.models;

import io.azam.ulidj.ULID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Version implements Serializable {
    @Id
    private String id;
    private Integer value;
    private String schema;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private Producer producer;
    @OneToMany(mappedBy = "version", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Consumer> consumers = Collections.emptyList();
    private LocalDateTime createdAt;

    public Version(Integer value, String schema) {
        this.id = ULID.random();
        this.value = value;
        this.schema = schema;
        this.createdAt = LocalDateTime.now();
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

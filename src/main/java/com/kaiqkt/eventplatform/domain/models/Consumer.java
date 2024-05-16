package com.kaiqkt.eventplatform.domain.models;

import io.azam.ulidj.ULID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {
    @Id
    private String id;
    private String service;
    private String url;
    private String contentType;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "version_id")
    private Version version;

    public Consumer(String service, String url, String contentType) {
        this.id = ULID.random();
        this.service = service;
        this.url = url;
        this.contentType = contentType;
        this.createdAt = LocalDateTime.now();
    }
}

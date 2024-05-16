package com.kaiqkt.eventplatform.domain.models;

import com.kaiqkt.eventplatform.domain.exception.DomainException;
import com.kaiqkt.eventplatform.domain.exception.ErrorType;
import io.azam.ulidj.ULID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producer implements Serializable {
    @Id
    private String id;
    private String service;
    private String action;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "producer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Version> versions = Collections.emptyList();

    public Producer(String service, String action, Version version) throws DomainException {
        this.id = ULID.random();
        this.service = service;
        this.action = action;
        this.versions = List.of(version);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public boolean hasVersion(Version version) {
        return this.getVersions().stream().map(Version::getValue).anyMatch(version.getValue()::equals);
    }

    public Version findVersionByValue(Integer value) throws DomainException {
        return this.getVersions().stream()
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new DomainException(ErrorType.VERSION_NOT_FOUND));
    }

    public Version getFirstVersion() throws DomainException {
        return getVersions().stream().findFirst().orElseThrow(() -> new DomainException(ErrorType.VERSION_NOT_FOUND));
    }

    public void setVersion(Version version) throws DomainException {
        this.versions.add(version);
    }

    public List<Version> getVersions() {
        if (this.versions != null) {
            return this.versions.stream()
                    .sorted(Comparator.comparingInt(Version::getValue))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public void removeVersion(Integer version) {
        this.versions.removeIf(v -> v.getValue().equals(version));
    }

    public boolean isVersionSequential(Version version) {
        return Objects.equals(version.getValue(), this.versions.size());
    }

    public boolean isFirstVersionValid() {
        if (this.versions.size() == 1) {
            return this.versions.get(0).getValue() == 1;
        }

        return false;
    }
}

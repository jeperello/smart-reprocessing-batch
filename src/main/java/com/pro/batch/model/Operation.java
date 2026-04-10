package com.pro.batch.model;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationStatus status;

    private int retryCount;
    private String lastError;

    @Version
    private Long version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}


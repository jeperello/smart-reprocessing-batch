package com.pro.batch.dto;

import com.pro.batch.model.OperationStatus;

import java.time.LocalDateTime;

public record OperationDto(Long id,
                           String status,
                           int retryCount,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
}

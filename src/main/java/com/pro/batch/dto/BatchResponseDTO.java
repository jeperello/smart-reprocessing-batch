package com.pro.batch.dto;

import java.time.LocalDateTime;

public record BatchResponseDTO(
        Long jobId,
        String status,
        LocalDateTime timestamp,
        String message,
        long pendingCount,
        long inProgressCount,
        long successCount,
        long failedCount,
        long retryCount,
        long totalRecords
) {}

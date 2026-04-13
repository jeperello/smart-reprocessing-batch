package com.pro.batch.service;

import com.pro.batch.dto.BatchResponseDTO;
import com.pro.batch.model.OperationStatus;
import com.pro.batch.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final OperationRepository  operationRepository;

    @Override
    public BatchResponseDTO getOperarionsStatus() {
        return new BatchResponseDTO(
                null, // No hay un JobId activo en una consulta de estado general
                "REPORT",
                LocalDateTime.now(),
                "Estadísticas actuales",
                operationRepository.countByStatus(OperationStatus.PENDING),
                operationRepository.countByStatus(OperationStatus.PROCESSING),
                operationRepository.countByStatus(OperationStatus.SUCCESS),
                operationRepository.countByStatus(OperationStatus.FAILED),
                operationRepository.countByStatus(OperationStatus.RETRY),
                operationRepository.count()
        );
    }
}

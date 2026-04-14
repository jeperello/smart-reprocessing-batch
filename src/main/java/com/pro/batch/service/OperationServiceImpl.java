package com.pro.batch.service;

import com.pro.batch.dto.OperationDto;
import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import com.pro.batch.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {
    OperationRepository operationRepository;

    @Override
    public List<OperationDto> getAllOperations() {
        return operationRepository.findAll().stream()
                .map(op -> new OperationDto(
                        op.getId(),
                        op.getStatus().name(),
                        op.getRetryCount(),
                        op.getCreatedAt(),
                        op.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public long countOperations(String status) {
        return operationRepository.countByStatus(OperationStatus.valueOf(status));
    }
}

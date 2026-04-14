package com.pro.batch.service;

import com.pro.batch.dto.OperationDto;
import com.pro.batch.model.Operation;

import java.util.List;

public interface OperationService {
    List<OperationDto> getAllOperations();
    long countOperations(String status);
}

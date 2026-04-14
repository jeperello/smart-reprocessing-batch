package com.pro.batch.controller;

import com.pro.batch.dto.OperationDto;
import com.pro.batch.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @GetMapping("/operations")
    public ResponseEntity<List<OperationDto>> getAllOperations() {
        return ResponseEntity.ok(operationService.getAllOperations());
    }

    @GetMapping("/operations/count")
    public ResponseEntity<Long> getStatus(@RequestParam(value = "status", defaultValue = "PENDING") String status) {
        return ResponseEntity.ok(operationService.countOperations(status));
    }
}

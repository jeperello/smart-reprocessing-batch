package com.pro.batch.service;

import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import com.pro.batch.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final OperationRepository repository;

    @Override
    public void createMockData(int count) {
        // Mock data creation logic here
        System.out.println("Creating " + count + " mock data entries...");
        // You can implement the actual logic to create mock data as needed
        List<Operation> operations = IntStream.rangeClosed(1, count)
                .mapToObj(i -> Operation.builder()
                        .status(OperationStatus.PENDING)
                        .retryCount(0)
                        .build())
                .toList();
        repository.saveAll(operations);
    }
}

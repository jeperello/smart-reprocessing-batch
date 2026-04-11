package com.pro.batch.config;

import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import com.pro.batch.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final OperationRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            log.info("Insertando datos de prueba...");
            var operations = IntStream.rangeClosed(1, 100)
                    .mapToObj(i -> Operation.builder()
                            .status(OperationStatus.PENDING)
                            .retryCount(0)
                            .build())
                    .toList();

            repository.saveAll(operations);
            log.info("¡100 operaciones listas para procesar!");
        }
    }
}

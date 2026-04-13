package com.pro.batch.batch;

import com.pro.batch.config.BatchConfig;
import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import com.pro.batch.repository.OperationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBatchTest
@SpringBootTest
class OperationJobIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private OperationRepository repository;

    @Test
    @DisplayName("El Job completo debe procesar registros de PENDING a SUCCESS")
    void testFullJobExecution() throws Exception {
        // GIVEN: 5 registros pendientes en la DB
        repository.saveAll(IntStream.range(0, 5)
                .mapToObj(i -> Operation.builder().status(OperationStatus.PENDING).build())
                .toList());

        // WHEN: Ejecutamos el Job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // THEN: El estado final del Job debe ser COMPLETED
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}

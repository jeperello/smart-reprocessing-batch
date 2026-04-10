package com.pro.batch.repository;

import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OperationRepositoryTest {

    @Autowired
    private OperationRepository repository;

    @Test
    void shouldSaveAndFindByStatus() {
        // Given
        Operation op = Operation.builder()
                .status(OperationStatus.PENDING)
                .retryCount(0)
                .build();
        repository.save(op);

        // When
        var result = repository.findByStatus(OperationStatus.PENDING, PageRequest.of(0, 10));

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo(OperationStatus.PENDING);
    }
}
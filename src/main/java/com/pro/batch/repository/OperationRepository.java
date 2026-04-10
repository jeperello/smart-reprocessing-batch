package com.pro.batch.repository;


import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    // Usamos Page para que el Reader del Batch sea escalable
    Page<Operation> findByStatus(OperationStatus status, Pageable pageable);

    // Para el reintento inteligente: buscamos FAILED con pocos reintentos
    Page<Operation> findByStatusAndRetryCountLessThan(OperationStatus status, int maxRetries, Pageable pageable);
}
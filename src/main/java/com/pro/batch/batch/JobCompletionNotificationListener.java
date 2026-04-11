package com.pro.batch.batch;

import com.pro.batch.model.OperationStatus;
import com.pro.batch.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final OperationRepository repository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINALIZADO !!!");

            long total = repository.count();
            long pending = repository.countByStatus(OperationStatus.PENDING);
            long processing = repository.countByStatus(OperationStatus.PROCESSING);
            long success = repository.countByStatus(OperationStatus.SUCCESS);
            long retry = repository.countByStatus(OperationStatus.RETRY);
            long failed = repository.countByStatus(OperationStatus.FAILED);

            log.info("---------------------------------------");
            log.info("AUDITORÍA TOTAL (Base de Datos):");
            log.info("Total registros en DB: {}", total);
            log.info("Pendientes:            {}", pending);
            log.info("En Proceso:            {}", processing);
            log.info("Exitosos:              {}", success);
            log.info("En Reintento:          {}", retry);
            log.info("Fallidos:              {}", failed);
            log.info("SUMA CONTROL:          {}", (pending + processing + success + retry + failed));
            log.info("---------------------------------------");
        }
    }
}

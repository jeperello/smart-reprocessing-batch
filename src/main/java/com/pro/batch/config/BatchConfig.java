package com.pro.batch.config;

import com.pro.batch.batch.OperationProcessor;
import com.pro.batch.batch.OperationWriter;
import com.pro.batch.model.Operation;
import com.pro.batch.repository.OperationRepository;
import com.pro.batch.model.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final OperationRepository repository;

    @Bean
    public RepositoryItemReader<Operation> reader() {
        return new RepositoryItemReaderBuilder<Operation>()
                .name("operationReader")
                .repository(repository)
                .methodName("findByStatus")
                .arguments(OperationStatus.PENDING)
                .pageSize(10)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public Step mainStep(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager,
                         OperationProcessor processor,
                         OperationWriter writer) {
        return new StepBuilder("mainStep", jobRepository)
                .<Operation, Operation>chunk(10, transactionManager)
                .reader(reader()) // Ahora coincidirá Operation con Operation
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job reprocessingJob(JobRepository jobRepository, Step mainStep) {
        return new JobBuilder("reprocessingJob", jobRepository)
                .start(mainStep)
                .build();
    }
}
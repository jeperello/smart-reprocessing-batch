package com.pro.batch.batch;

import com.pro.batch.model.Operation;
import com.pro.batch.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationWriter implements ItemWriter<Operation> {

    private final OperationRepository repository;

    @Override
    public void write(Chunk<? extends Operation> chunk) {
        log.info("Escribiendo {} items en la base de datos", chunk.size());
        repository.saveAll(chunk);
    }
}
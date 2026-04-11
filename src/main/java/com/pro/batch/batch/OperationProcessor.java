package com.pro.batch.batch;

import com.pro.batch.model.Operation;
import com.pro.batch.model.OperationStatus;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class OperationProcessor implements ItemProcessor<Operation, Operation> {

    private final Random random = new Random();

    @Override
    public Operation process(Operation item) {
        if (item.getStatus() == OperationStatus.SUCCESS) {
            return null;
        }
        item.setStatus(OperationStatus.PROCESSING);
        try {
            // Simulación de lógica de negocio (Ej: Llamada a un servicio externo)
            doBusinessLogic(item);

            item.setStatus(OperationStatus.SUCCESS);
            item.setLastError(null);
        } catch (Exception e) {
            handleFailure(item, e.getMessage());
        }
        return item;
    }

    private void doBusinessLogic(Operation item) {
        // Simulamos un fallo aleatorio del 30%
        if (random.nextInt(10) < 3) {
            throw new RuntimeException("Error de conexión con el Core Bancario");
        }
    }

    private void handleFailure(Operation item, String error) {
            item.setRetryCount(item.getRetryCount() + 1);
        item.setLastError(error);

        // Lógica de reintentos: máximo 3
        if (item.getRetryCount() >= 3) {
            item.setStatus(OperationStatus.FAILED);
        } else {
            item.setStatus(OperationStatus.RETRY);
        }
    }
}
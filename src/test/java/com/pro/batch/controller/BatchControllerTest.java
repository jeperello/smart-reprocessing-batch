package com.pro.batch.controller;
import com.pro.batch.dto.BatchResponseDTO;
import com.pro.batch.service.DataService;
import com.pro.batch.service.StatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // La nueva anotación
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatchController.class)
class BatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Usamos @MockitoBean (Spring Boot 3.4+)
    @MockitoBean
    private JobLauncher jobLauncher;

    @MockitoBean
    private Job reprocessingJob;

    @MockitoBean
    private StatusService statusService;

    @MockitoBean
    private DataService dataService;

    @Test
    @DisplayName("Debe retornar el status con las métricas en formato JSON")
    void shouldReturnStatusMetrics() throws Exception {
        // GIVEN: Simulamos la respuesta del record
        BatchResponseDTO mockResponse = new BatchResponseDTO(
                null,
                "REPORT",
                LocalDateTime.now(),
                "Estadísticas actuales",
                200L,
                0L,
                80L,
                0L,
                20L,
                100L
        );

        // Configuramos el comportamiento del mock
        when(statusService.getOperarionsStatus()).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/batch/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REPORT"))
                .andExpect(jsonPath("$.successCount").value(80))
                .andExpect(jsonPath("$.totalRecords").value(100));
    }
}
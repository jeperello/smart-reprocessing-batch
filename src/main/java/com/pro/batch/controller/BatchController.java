package com.pro.batch.controller;

import com.pro.batch.dto.BatchResponseDTO;
import com.pro.batch.service.StatusServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job reprocessingJob;
    private final StatusServiceI statusService;

    @PostMapping("/run")
    public ResponseEntity<String> runBatch() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(reprocessingJob, params);
        return ResponseEntity.accepted().body("Batch iniciado");
    }

    @GetMapping("/status")
    public ResponseEntity<BatchResponseDTO> getStatus() {
        return ResponseEntity.ok(statusService.getOperarionsStatus());
    }
}

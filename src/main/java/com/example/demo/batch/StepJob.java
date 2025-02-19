package com.example.demo.batch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class StepJob implements Job{

    private final List<Step> steps;
    private final  JobExecutionListener jobExecutionListener;

    public StepJob(List<Step> steps, JobExecutionListener jobExecutionListener) {
        this.steps = steps;
        this.jobExecutionListener = Objects.requireNonNullElseGet(jobExecutionListener, () -> new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {

            }

            @Override
            public void afterJob(JobExecution jobExecution) {

            }
        });
    }

    @Override
    public JobExecution execute() {
        final JobExecution jobExecution = new JobExecution();
        jobExecution.setStatus(BatchStatus.STARTING);
        jobExecution.setStartTime(LocalDateTime.now());

        // 전처리
        jobExecutionListener.beforeJob(jobExecution);



        try {
            // 비즈니스 로직
            steps.forEach(Step::execute);
            jobExecution.setStatus(BatchStatus.COMPLETED);

        } catch (Exception e) {
            jobExecution.setStatus(BatchStatus.FAILED);
        }

        jobExecution.setEndTime(LocalDateTime.now());

        // 후처리
        jobExecutionListener.afterJob(jobExecution);

        return jobExecution;
    }
}

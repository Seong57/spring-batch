package com.example.demo.batch;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;

public class TaskletJob implements Job {

    private final Tasklet tasklet;
    private final JobExecutionListener jobExecutionListener;

    public TaskletJob(Tasklet tasklet, JobExecutionListener jobExecutionListener) {
        this.tasklet = tasklet;
        this.jobExecutionListener = Objects.requireNonNullElseGet(jobExecutionListener, () -> new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {

            }

            @Override
            public void afterJob(JobExecution jobExecution) {

            }
        });
    }

    @Builder
    public TaskletJob(ItemReader<?> itemReader, ItemProcessor<?, ?> itemProcessor, ItemWriter<?> itemWriter, JobExecutionListener jobExecutionListener) {
        this(new SimpleTasklet(itemReader, itemProcessor, itemWriter), jobExecutionListener );
    }

    public JobExecution execute() {

        final JobExecution jobExecution = new JobExecution();
        jobExecution.setStatus(BatchStatus.STARTING);
        jobExecution.setStartTime(LocalDateTime.now());

        // 전처리
        jobExecutionListener.beforeJob(jobExecution);



        try {
            // 비즈니스 로직
            tasklet.execute();
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

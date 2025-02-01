package com.example.demo.application;

import com.example.demo.batch.Job;
import com.example.demo.batch.TaskletJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchConfiguration {

    @Bean
    public Job dormantBatchJob(
            DormantBatchItemReader dormantBatchItemReader,
            DormantBatchProcessor dormantBatchProcessor,
            DormantBatchWriter dormantBatchWriter,
            DormantBatchJobExecutionListener dormantBatchJobExecutionListener
    ) {
        return TaskletJob.builder()
                .itemReader(dormantBatchItemReader)
                .itemProcessor(dormantBatchProcessor)
                .itemWriter(dormantBatchWriter)
                .jobExecutionListener(dormantBatchJobExecutionListener)
                .build();
    }
}

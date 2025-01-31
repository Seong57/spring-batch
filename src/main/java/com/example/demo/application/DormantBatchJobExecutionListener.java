package com.example.demo.application;

import com.example.demo.EmailProvider;
import com.example.demo.batch.JobExecution;
import com.example.demo.batch.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchJobExecutionListener implements JobExecutionListener {

    private final EmailProvider emailProvider;

    public DormantBatchJobExecutionListener() {
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // no-op
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        emailProvider.send(
                "admin@test.com",
                "배치 완료 알림",
                "배치 작업 수행, status : " + jobExecution.getStatus());
    }
}

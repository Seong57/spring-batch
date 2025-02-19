package com.example.demo.application.step;

import com.example.demo.batch.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StepExampleBatchConfigTest {
    @Autowired
    private Job stepExampleBatchJob;

    @Test
    void test(){
        stepExampleBatchJob.execute();
    }

}
package com.example.demo.application.step;

import com.example.demo.batch.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class StepExampleBatchConfig {

    @Bean
    public Job stepExampleBatchJob(
            Step step1,
            Step step2,
            Step step3
    ){
        return new StepJobBuilder()
                .start(step1)
                .next(step2)
                .next(step3)
                .build();

    }

    @Bean
    public Step step1(){

        Tasklet tasklet = () -> System.out.println("step 1");
        return new Step(tasklet);
    }

    @Bean
    public Step step2(){
        Tasklet tasklet = () -> System.out.println("step 2");
        return new Step(tasklet);
    }

    @Bean
    public Step step3(){
        Tasklet tasklet = () -> System.out.println("step 3");
        return new Step(tasklet);
    }
}

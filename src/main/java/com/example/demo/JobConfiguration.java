package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class JobConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("job-chunk", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager
//                     @Value("#{jobParameters['name']}") String name
    ) {
//        log.info("name : {}", name);
        return new StepBuilder("step", jobRepository)
                .tasklet((a, b) -> RepeatStatus.FINISHED, platformTransactionManager)
                .build();
    }

    /*@Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){

        ItemReader<Integer> itemReader = new ItemReader<>() {
            private int count = 0;

            @Override
            public Integer read() {
                count++;

                log.info("Read {}", count);

                if (count == 20) {
                    return null;
                }

//                if (count >= 15)
//                    throw new IllegalStateException("예외 발생");

                return count;
            }
        };

        ItemProcessor<Integer, Integer> itemProcessor = new ItemProcessor<>() {
            @Override
            public Integer process(Integer item) throws Exception {
                if (item == 15)
                    throw new IllegalStateException();
                return item;
            }
        };

        return new StepBuilder("step", jobRepository)
                .<Integer, Integer>chunk(10, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(read -> {})
                .faultTolerant()
                .retry(IllegalStateException.class)
                .retryLimit(5)
                .build();
    }*/
}

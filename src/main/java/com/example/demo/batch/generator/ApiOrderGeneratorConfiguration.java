package com.example.demo.batch.generator;

import com.example.demo.domain.ApiOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 요구사항
 * 유료 Api 호출 이력을 배치작업을 통해 파일로 만듦
 */

@Configuration
@RequiredArgsConstructor
public class ApiOrderGeneratorConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job apiOrderGenerator(Step step) {
        return new JobBuilder("apiOrderGeneratorJob", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .validator(
                        new DefaultJobParametersValidator(
                                new String[]{"targetDate", "totalCount"}, new String[0]
                        )
                )
                .build();
    }

    @Bean
    public Step step(
            ApiOrderGeneratorReader  apiOrderGeneratorReader,
            ApiOrderGeneratorProcessor apiOrderGeneratorProcessor
    ) {
        return new StepBuilder("apiOrderGenerator", jobRepository)
                .<Boolean, ApiOrder>chunk(5000, platformTransactionManager)
                .reader(apiOrderGeneratorReader)
                .processor(apiOrderGeneratorProcessor)
                .writer(apiOrderFlatFileItemWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<ApiOrder> apiOrderFlatFileItemWriter(
            @Value("#{jobParameters['targetDate']}") String targetDate
    ) {
        String fileName = targetDate + "api_orders.csv";

        return new FlatFileItemWriterBuilder<ApiOrder>()
                .name("apiOrderFlatFileItemWriter")
                .resource(new PathResource("src/main/resources/dataset/" + fileName))
                .delimited() // ","
                .names("id", "customerId", "url", "state", "createdAt")
                .headerCallback(writer -> writer.write("id,customerId,url,state,createdAt"))
                .build();
    }

}

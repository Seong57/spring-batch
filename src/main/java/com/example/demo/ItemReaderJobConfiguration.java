package com.example.demo;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
public class ItemReaderJobConfiguration {

    @Bean
    public Job job(
            JobRepository jobRepository,
            Step step
    ) {
        return new JobBuilder("itemReaderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            ItemReader<User> jpaCursorItemReader
    ) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(2, platformTransactionManager)
                .reader(jpaCursorItemReader)
                .writer(System.out::println)
                .build();
    }

    @Bean
    public FlatFileItemReader<User> flatFileItemReader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource("users.txt"))
                .linesToSkip(2)
                .delimited().delimiter(",")
                .names("name", "age", "region", "telephone")
                .targetType(User.class)
                .strict(false)
                .build();
    }

    @Bean
    public FlatFileItemReader<User> fixedLengthFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<User>()
                .name("fixedLengthFlatFileItemReader")
                .resource(new ClassPathResource("fixedLengthUsers.txt"))
                .linesToSkip(2)
                .fixedLength()
                .columns(new Range[]{new Range(1, 2), new Range(3, 4), new Range(5, 6), new Range(7, 19)})
                .names("name", "age", "region", "telephone")
                .targetType(User.class)
                .strict(false)
                .build();
    }

    @Bean
    public JsonItemReader<User> jsonItemReader() {
        return new JsonItemReaderBuilder<User>()
                .name("jsonItemReader")
                .resource(new ClassPathResource("users.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(User.class))
                .build();
    }

    // paging 은 db와 페이지 단위로 붙었다 떨어졌다 반복
    @Bean
    public ItemReader<User> jpaPagingItemReader(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<User>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(3)
                .queryString("SELECT u FROM User u ORDER BY u.id")
                .build();
    }

    // 커서는 계속 db와 붙어있음
    @Bean
    public ItemReader<User> jpaCursorItemReader(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<User>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT u FROM User u ORDER BY u.id")
                .build();
    }
}

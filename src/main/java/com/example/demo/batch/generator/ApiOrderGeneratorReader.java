package com.example.demo.batch.generator;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@StepScope  // job parameter 를 받기 위
public class ApiOrderGeneratorReader implements ItemReader<Boolean> {

    private Long totalCount;
    // 동시성에 안전한 Long
    private AtomicLong current;

    // edit configuration 에 program arguments 를 추가해준다! (totalCount=100)!
    public ApiOrderGeneratorReader(
            @Value("#{jobParameters['totalCount']}") String totalCount
    ) {
        this.totalCount = Long.parseLong(totalCount);
        this.current = new AtomicLong(0);
    }

    @Override
    public Boolean read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // incrementAndGet() ++ 후 get
        // args 에 totalCount 를 100으로 설정했는데 100개를 넘어가면 종
        // 즉 100번의 read 작업 수행
        if (current.incrementAndGet() > totalCount)
            return null;

        return true;
    }
}

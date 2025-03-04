package com.example.demo.batch.generator;

import com.example.demo.domain.ApiOrder;
import com.example.demo.domain.ServicePolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@Component
public class ApiOrderGeneratorProcessor implements ItemProcessor<Boolean, ApiOrder> {

    // 랜덤한 고객 id와 서비스폴리시(유료 api의 url, 요금)을 만들어주자
    private final List<Long> customerIds = LongStream.range(0, 20).boxed().toList();
    private final List<ServicePolicy> servicePolicies = Arrays.stream(ServicePolicy.values()).toList();
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public ApiOrder process(Boolean item) throws Exception {

        Long randomCustomerId = customerIds.get(random.nextInt(customerIds.size()));
        ServicePolicy randomServicePolicy = servicePolicies.get(random.nextInt(servicePolicies.size()));
        // 5번중 1번은 실패
        ApiOrder.State randomState = random.nextInt(5) % 5 == 1 ?
                ApiOrder.State.FAIL : ApiOrder.State.SUCCESS;

        return new ApiOrder(
                UUID.randomUUID().toString(),
                randomCustomerId,
                randomServicePolicy.getUrl(),
                randomState,
                LocalDateTime.now().format(dateTimeFormatter)
        );
    }

}

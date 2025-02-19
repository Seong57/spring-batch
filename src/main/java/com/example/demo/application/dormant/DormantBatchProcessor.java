package com.example.demo.application.dormant;

import com.example.demo.batch.ItemProcessor;
import com.example.demo.customer.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DormantBatchProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) {
        boolean isDormantTarget = LocalDateTime.now()
                    .minusDays(365)
                    .isAfter(item.getLoginAt());

        if(isDormantTarget){
            item.setStatus(Customer.Status.DORMANT);
            return item;
        }else {
            return null;
        }
    }
}

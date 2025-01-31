package com.example.demo.application;

import com.example.demo.batch.ItemReader;
import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchItemReader implements ItemReader<Customer> {

    private final CustomerRepository customerRepository;
    private int pageNo =  0;

    public DormantBatchItemReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer read() {

        PageRequest pageRequest = PageRequest.of(pageNo, 1, Sort.by("id").ascending());
        Page<Customer> page = customerRepository.findAll(pageRequest);

        final Customer customer;
        if(page.isEmpty()){
            pageNo = 0;
            return null;
        }
        else {
            pageNo++;
            return page.getContent().get(0);
        }
    }
}

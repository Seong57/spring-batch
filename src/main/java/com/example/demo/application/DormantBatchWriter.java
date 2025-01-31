package com.example.demo.application;

import com.example.demo.EmailProvider;
import com.example.demo.batch.ItemWriter;
import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchWriter implements ItemWriter<Customer> {

    private final CustomerRepository customerRepository;
    private final EmailProvider emailProvider;

    public DormantBatchWriter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void write(Customer item) {
        customerRepository.save(item);
        emailProvider.send(item.getEmail(), "휴면 전환 안내 메일", "내용");
    }
}

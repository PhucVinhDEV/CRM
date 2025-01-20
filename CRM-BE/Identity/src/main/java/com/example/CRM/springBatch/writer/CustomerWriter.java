package com.example.CRM.springBatch.writer;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.CustomerEAV.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CustomerWriter {

    private final CustomerRepository customerRepository;

    @Bean
    @Qualifier("CustomerWriter")
    public RepositoryItemWriter<Customer> writer(){
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }
}

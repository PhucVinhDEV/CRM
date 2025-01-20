package com.example.CRM.springBatch.processor;

import com.example.CRM.CustomerEAV.model.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CustomerProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        //All bussiness logic
        return item;
    }
}

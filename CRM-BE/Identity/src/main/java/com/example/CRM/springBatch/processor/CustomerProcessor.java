package com.example.CRM.springBatch.processor;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.common.util.DateTimeUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomerProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        //All bussiness logic
        item.setId(UUID.randomUUID());
        item.setVersion(1);
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("anonymous");
        item.setLastModifiedAt(null);
        item.setLastModifiedBy("anonymous");
        return item;
    }
}

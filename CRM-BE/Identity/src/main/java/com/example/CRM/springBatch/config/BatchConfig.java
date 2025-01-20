package com.example.CRM.springBatch.config;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.springBatch.listener.CustomItemReadListener;
import com.example.CRM.springBatch.listener.FileCleanupListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static org.apache.commons.io.IOUtils.writer;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;


    @Bean
    public Step importCustomer(
            @Qualifier("customerFileReader") ItemReader<Customer> customerReader,
             ItemProcessor<Customer, Customer> processor,
            @Qualifier("CustomerWriter") ItemWriter<Customer> writer
    ){
        return new StepBuilder("customerImport",jobRepository)
                .<Customer,Customer>chunk(10,platformTransactionManager)
                .reader(customerReader)
                .processor(processor)
                .writer(writer)
                .listener(new CustomItemReadListener())
                .build();
    };

    @Bean
    public Job ImportCustomerJob(Step importCustomer, FileCleanupListener fileCleanupListener){
        return new JobBuilder("importCustomer",jobRepository)
                .start(importCustomer)
                .listener(fileCleanupListener)
                .build();
    }
}

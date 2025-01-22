package com.example.CRM.springBatch.config;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.springBatch.listener.CustomItemReadListener;
import com.example.CRM.springBatch.listener.FileCleanupListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final DataSource dataSource;
    private final JobRepository jobRepository;
//    @Bean
//    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTransactionManager(transactionManager);
//        factory.setDatabaseType("postgres"); // hoặc loại database bạn đang sử dụng
//        return factory.getObject();
//    }

//    @Bean
//    public TaskExecutorJobLauncher jobLauncher(JobRepository jobRepository) {
//        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
//        jobLauncher.setJobRepository(jobRepository);
//        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); // Sử dụng TaskExecutor để chạy job bất đồng bộ
//        return jobLauncher;
//    }

    @Bean
    public Step importCustomer(
            @Qualifier("customerFileReader") ItemReader<Customer> customerReader,
            ItemProcessor<Customer, Customer> processor,
            @Qualifier("CustomerJdbcWriter") ItemWriter<Customer> writer,
            PlatformTransactionManager platformTransactionManager // Thêm PlatformTransactionManager
    ) throws Exception {
        return new StepBuilder("customerImport", jobRepository)
                .<Customer, Customer>chunk(50, platformTransactionManager) // Sử dụng platformTransactionManager
                .reader(customerReader)
                .processor(processor)
                .writer(writer)
                .listener(new CustomItemReadListener())
                .build();
    }

    @Bean
    public Job ImportCustomerJob(
            Step importCustomer,
            FileCleanupListener fileCleanupListener,
            JobRepository jobRepository // Thêm JobRepository
    ) {
        return new JobBuilder("importCustomer", jobRepository)
                .start(importCustomer)
                .listener(fileCleanupListener)
                .build();
    }
}

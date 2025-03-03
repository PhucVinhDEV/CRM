package com.example.CRM.Customer.springBatch.reader;

import com.example.CRM.Customer.CustomerEAV.model.Customer;
import com.example.CRM.Customer.springBatch.mapper.CustomerFieldSetMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CustomerReader {

    @Bean
    @Qualifier("customerFileReader")
    @StepScope // Đánh dấu bean là StepScope để có thể truy cập JobParameters
    public FlatFileItemReader<Customer> customerFileReader(
            @Value("#{jobParameters['inputFile']}") String inputFilePath) {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(inputFilePath)); // Sử dụng đường dẫn file từ JobParameters
        reader.setLinesToSkip(1);
        reader.setEncoding("UTF-8"); // Đặt mã hóa phù hợp
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("Email", "First Name", "Last Name", "Phone", "dob"); // Tên cột trong file CSV

        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
        lineMapper.setLineTokenizer(lineTokenizer);
        return lineMapper;
    }


}

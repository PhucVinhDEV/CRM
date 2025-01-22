package com.example.CRM.springBatch.reader;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.springBatch.mapper.CustomerFieldSetMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
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

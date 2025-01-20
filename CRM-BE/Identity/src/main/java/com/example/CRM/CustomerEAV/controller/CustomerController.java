package com.example.CRM.CustomerEAV.controller;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.CustomerEAV.model.DTO.CustomerDTO;
import com.example.CRM.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.CustomerEAV.service.CustomerService;
import com.example.CRM.FIOStream.Model.ImportResponse;
import com.example.CRM.FIOStream.Service.ImportFileService;
import com.example.CRM.common.reponsese.ApiReponsese;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.validate.group.InsertInfo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/Customer")
@Validated
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private CustomerService customerService;
    private JobLauncher jobLauncher;
    private Job importCustomerJob;
    @PostMapping
    public ApiReponsese<CustomerDTO> addCustomer(@Validated(InsertInfo.class)  @RequestBody CustomerRecord customer) {
        return  ApiReponsese.<CustomerDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(customerService.save(customer))
                .build();
    }
    @PostMapping("/import")
    public ApiReponsese<ImportResponse> importCustomers(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo file tạm thời
            File tempFile = File.createTempFile("upload", ".csv");
            file.transferTo(tempFile);

            // Truyền đường dẫn file tạm vào job parameters
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("inputFile", tempFile.getAbsolutePath())
                    .toJobParameters();
            log.info("path: {}", tempFile.getAbsolutePath());
            // Chạy job
            JobExecution jobExecution = jobLauncher.run(importCustomerJob, jobParameters);

            // Lấy thông tin từ job execution (ví dụ: số lượng bản ghi đã xử lý)
            int totalRecords = jobExecution.getStepExecutions().stream()
                    .mapToInt(stepExecution -> Math.toIntExact(stepExecution.getWriteCount()))
                    .sum();

            // Xóa file tạm sau khi job hoàn thành
//            tempFile.delete();

            // Trả về kết quả
            return ApiReponsese.<ImportResponse>builder()
                    .timestamp(DateTimeUtil.now())
                    .result(ImportResponse.builder()
                            .totalRecords(totalRecords) // Số lượng bản ghi đã import
                            .build())
                    .build();
        } catch (IOException e) {
            return ApiReponsese.<ImportResponse>builder()
                    .timestamp(DateTimeUtil.now())
                    .message("Failed to upload file: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return ApiReponsese.<ImportResponse>builder()
                    .timestamp(DateTimeUtil.now())
                    .message("Failed to start job: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ApiReponsese<CustomerDTO> getCustomerById(@PathVariable("id") UUID id) {
        return ApiReponsese.<CustomerDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(customerService.getById(id))  // Giả sử bạn có phương thức getCustomerById trong service
                .build();
    }
}


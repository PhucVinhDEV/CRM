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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/Customer")
@Validated
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private CustomerService customerService;
    private final ImportFileService importFileService;
    @PostMapping
    public ApiReponsese<CustomerDTO> addCustomer(@Validated(InsertInfo.class)  @RequestBody CustomerRecord customer) {
        return  ApiReponsese.<CustomerDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(customerService.save(customer))
                .build();
    }
    @PostMapping("/import")
    public ApiReponsese<ImportResponse> importCustomers(@RequestParam("file") MultipartFile file){
        log.info("Input" + file.getName());
        List<Customer> importedCustomers = importFileService.parseDataToCustomerRecords(file);
        return ApiReponsese.<ImportResponse>builder()
                .timestamp(DateTimeUtil.now())
                .result(ImportResponse.builder()
                        .totalRecords(importedCustomers.size())
                        .build())
                .build();
    }

    @GetMapping("/{id}")
    public ApiReponsese<CustomerDTO> getCustomerById(@PathVariable("id") UUID id) {
        return ApiReponsese.<CustomerDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(customerService.getById(id))  // Giả sử bạn có phương thức getCustomerById trong service
                .build();
    }
}


package com.example.CRM.CustomerEAV.controller;

import com.example.CRM.CustomerEAV.model.DTO.CustomerDTO;
import com.example.CRM.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.CustomerEAV.service.CustomerService;
import com.example.CRM.common.reponsese.ApiReponsese;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.validate.group.InsertInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/Customer")
@Validated
public class CustomerController {
    private CustomerService customerService;

    @PostMapping
    public ApiReponsese<CustomerDTO> addCustomer(@Validated(InsertInfo.class)  @RequestBody CustomerRecord customer) {
        return  ApiReponsese.<CustomerDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(customerService.save(customer))
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


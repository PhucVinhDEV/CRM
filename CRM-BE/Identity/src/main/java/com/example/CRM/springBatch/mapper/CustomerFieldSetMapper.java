package com.example.CRM.springBatch.mapper;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.common.util.DateTimeUtil;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
    @Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
        Customer customer = new Customer();
        customer.setEmail(fieldSet.readString("Email"));
        customer.setFirstName(fieldSet.readString("First Name"));
        customer.setLastName(fieldSet.readString("Last Name"));
        customer.setPhone(fieldSet.readString("Phone"));

        // Chuyển đổi chuỗi ngày thành LocalDate
        String dobString = fieldSet.readString("dob");
        if (!dobString.isEmpty()) {

            customer.setDob(DateTimeUtil.parseDate(dobString));
        }

        return customer;
    }
}

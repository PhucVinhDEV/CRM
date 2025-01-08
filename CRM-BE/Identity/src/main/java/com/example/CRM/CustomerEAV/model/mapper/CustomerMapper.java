package com.example.CRM.CustomerEAV.model.mapper;

import com.example.CRM.CustomerEAV.model.Customer;
import com.example.CRM.CustomerEAV.model.DTO.CustomerDTO;
import com.example.CRM.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.common.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends GenericMapper<CustomerRecord, Customer, CustomerDTO> {
}

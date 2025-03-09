package com.example.CRM.Customer.CustomerEAV.model.mapper;

import com.example.CRM.Customer.CustomerEAV.model.Customer;
import com.example.CRM.Customer.CustomerEAV.model.DTO.CustomerDTO;
import com.example.CRM.Customer.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.common.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper extends GenericMapper<CustomerRecord, Customer, CustomerDTO> {
}

package com.example.CRM.CustomerEAV.service;

import com.example.CRM.CustomerEAV.model.*;
import com.example.CRM.CustomerEAV.model.DTO.CustomerDTO;
import com.example.CRM.CustomerEAV.model.mapper.CustomerMapper;
import com.example.CRM.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.CustomerEAV.repository.*;
import com.example.CRM.CustomerEAV.util.TypeOfValue;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface CustomerService {
    CustomerDTO save(CustomerRecord record);
}

@Service
@AllArgsConstructor
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AttributeRepository attributeRepository;
    private final StringAttributeValueRepository stringAttributeValueRepository;
    private final NumberAttributeValueRepository numberAttributeValueRepository;
    private final DateAttributeValueRepository dateAttributeValueRepository;

    @Override
    @Transactional
    public CustomerDTO save(CustomerRecord record) {
        // Tạo Customer từ record
        Customer customer = customerMapper.maptoEntity(record);
        customerRepository.save(customer);

      if(!record.attributes().isEmpty()){
          // Lưu các AttributeValue
          record.attributes().forEach((key, value) -> {
              Attribute attribute = attributeRepository.findByAttributeName(key)
                      .orElseThrow(() -> new RuntimeException("Không tìm thấy thuộc tính: " + key));

              if (value instanceof String stringValue) {
                  saveStringAttributeValue(customer, attribute, stringValue);
              } else if (value instanceof Double numberValue) {
                  saveNumberAttributeValue(customer, attribute, numberValue);
              } else if (value instanceof LocalDate dateValue) {
                  saveDateAttributeValue(customer, attribute, dateValue);
              } else {
                  throw new RuntimeException("Kiểu giá trị không được hỗ trợ: " + value.getClass().getSimpleName());
              }
          });
      }

        // Trả về DTO
        return customerMapper.maptoDto(customer);
    }

    private void saveStringAttributeValue(Customer customer, Attribute attribute, String value) {
        StringAttributeValue stringAttributeValue = new StringAttributeValue();
        stringAttributeValue.setCustomer(customer);
        stringAttributeValue.setAttribute(attribute);
        stringAttributeValue.setTypeOfValue(TypeOfValue.STRING);
        stringAttributeValue.setValue(value);
        stringAttributeValueRepository.save(stringAttributeValue);
    }

    private void saveNumberAttributeValue(Customer customer, Attribute attribute, Double value) {
        NumberAttributeValue numberAttributeValue = new NumberAttributeValue();
        numberAttributeValue.setCustomer(customer);
        numberAttributeValue.setAttribute(attribute);
        numberAttributeValue.setTypeOfValue(TypeOfValue.NUMBER);
        numberAttributeValue.setValue(value);
        numberAttributeValueRepository.save(numberAttributeValue);
    }

    private void saveDateAttributeValue(Customer customer, Attribute attribute, LocalDate value) {
        DateAttributeValue dateAttributeValue = new DateAttributeValue();
        dateAttributeValue.setCustomer(customer);
        dateAttributeValue.setAttribute(attribute);
        dateAttributeValue.setTypeOfValue(TypeOfValue.DATE);
        dateAttributeValue.setValue(value);
        dateAttributeValueRepository.save(dateAttributeValue);
    }
}

package com.example.CRM.Customer.CustomerEAV.service;

import com.example.CRM.Customer.CustomerEAV.model.*;
import com.example.CRM.Customer.CustomerEAV.model.DTO.CustomerDTO;
import com.example.CRM.Customer.CustomerEAV.model.mapper.CustomerMapper;
import com.example.CRM.Customer.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.Customer.CustomerEAV.repository.*;
import com.example.CRM.Customer.CustomerEAV.util.TypeOfValue;

import com.example.CRM.common.util.MessageUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CustomerService {
    CustomerDTO save(CustomerRecord record);
    CustomerDTO getById(UUID CustomerId);
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

        if (!record.attributes().isEmpty()) {
            // Lưu các AttributeValue
            record.attributes().forEach((key, value) -> {
                Attribute attribute = attributeRepository.findByAttributeName(key)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy thuộc tính: " + key));

                switch (attribute.getType()) {
                    case TypeOfValue.STRING:
                        saveStringAttributeValue(customer, attribute, value.toString());
                        break;
                    case TypeOfValue.NUMBER:
                        saveNumberAttributeValue(customer, attribute, Double.valueOf(value.toString()));
                        break;
                    case TypeOfValue.DATE:
                        saveDateAttributeValue(customer, attribute, LocalDate.parse(value.toString()));
                        break;
                    default:
                        throw new IllegalArgumentException("Loại giá trị không hợp lệ: " + attribute.getType());
                }
            });
        }

        // Trả về DTO
        return customerMapper.maptoDto(customer);
    }

    @Override
    public CustomerDTO getById(UUID CustomerId) {
        Customer customer = customerRepository.findById(CustomerId).orElseThrow(
                () -> new RuntimeException(MessageUtil.CUSTOMER_NOT_FOUND)
        );
        Map<String,String> values = parseAttributeValue(CustomerId);
        CustomerDTO customerDTO = customerMapper.maptoDto(customer);
        customerDTO.setAttributes(values);
        return customerDTO;
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

    private Map<String,String> parseAttributeValue(UUID CustomerId){
        List<Object[]> results = customerRepository.getAllAttributeValueByCustomer(CustomerId);
        Map<String, String> attributeMap = new HashMap<>();
        // Duyệt qua kết quả và tạo Map từ các cột
        for (Object[] result : results) {
            String attributeName = (String) result[0];  // Giả sử cột 0 là attribute_name
            String value = (String) result[1];          // Giả sử cột 1 là value
            attributeMap.put(attributeName, value);
        }
        return attributeMap;
    }
}

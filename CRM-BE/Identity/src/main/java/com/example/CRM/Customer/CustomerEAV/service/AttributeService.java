package com.example.CRM.Customer.CustomerEAV.service;


import com.example.CRM.Customer.CustomerEAV.model.Attribute;
import com.example.CRM.Customer.CustomerEAV.model.DTO.AttributeDTO;
import com.example.CRM.Customer.CustomerEAV.repository.AttributeRepository;
import com.example.CRM.Customer.CustomerEAV.model.record.AttributeRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface AttributeService {

    AttributeDTO save(AttributeRecord record);

    AttributeDTO getById(Integer id);

    void delete(Integer id);
}
@Service
@RequiredArgsConstructor
class  AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    @Override
    public AttributeDTO save(AttributeRecord record) {
        return this.toDTO(attributeRepository.save(this.toEntity(record)));
    }

    @Override
    public AttributeDTO getById(Integer id) {
        Attribute attribute = this.attributeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Attribute not found")
        );
        return this.toDTO(attribute);
    }


    @Override
    public void delete(Integer id) {
        attributeRepository.deleteAttributeAndValues(id);
    }

    private AttributeDTO toDTO(Attribute attribute) {
        return new AttributeDTO(
                attribute.getAttributeId(),
                attribute.getAttributeName(),
                attribute.getType()
        );
    }

    private Attribute toEntity(AttributeRecord record) {
        return new Attribute(
                record.id(),
                record.attributeName(),
                record.type()
        );
    }
}
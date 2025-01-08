package com.example.CRM.CustomerEAV.model;

import com.example.CRM.CustomerEAV.util.TypeOfValue;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AttributeValue {

    @Id
    private Long Id;

    @ManyToOne
    @JoinColumn(name = JoinTableUtil.EAV_MAPPING_CUSTOMER)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = JoinTableUtil.EAV_MAPPING_ATTRIBUTE)
    private Attribute attribute;

    @Column(nullable = false)
    private TypeOfValue typeOfValue;
}

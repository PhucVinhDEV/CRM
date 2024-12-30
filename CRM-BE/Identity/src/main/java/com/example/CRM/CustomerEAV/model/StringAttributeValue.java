package com.example.CRM.CustomerEAV.model;

import com.example.CRM.common.model.BaseEntity;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StringAttributeValue extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = JoinTableUtil.EAV_MAPPING_CUSTOMER)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = JoinTableUtil.EAV_MAPPING_ATTRIBUTE)
    private Attribute attribute;

    private String value;
}

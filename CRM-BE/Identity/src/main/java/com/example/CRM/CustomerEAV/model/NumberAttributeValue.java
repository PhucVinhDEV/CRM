package com.example.CRM.CustomerEAV.model;

import com.example.CRM.common.model.BaseEntity;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class NumberAttributeValue extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = JoinTableUtil.EAV_MAPPING_CUSTOMER)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = JoinTableUtil.EAV_MAPPING_ATTRIBUTE)
    private Attribute attribute;

    private BigDecimal value;
}

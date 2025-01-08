package com.example.CRM.CustomerEAV.model;

import com.example.CRM.common.model.BaseEntity;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class NumberAttributeValue extends AttributeValue {

    @Column(nullable = false)
    private Double value;


}

package com.example.CRM.CustomerEAV.model;

import com.example.CRM.common.model.BaseEntity;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StringAttributeValue extends AttributeValue {

    @Column(nullable = false)
    private String value;
}

package com.example.CRM.CustomerEAV.model;

import com.example.CRM.common.model.BaseEntity;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = NumberAttributeValue.NumberAttributeValueEntity.TABLE_NAME)
public class NumberAttributeValue extends AttributeValue {

    @Column(nullable = false)
    private Double value;

    @UtilityClass
    static class NumberAttributeValueEntity {
        public static final String TABLE_NAME = "J_NUMBERATTRIBUTEVALUE";
    }

}

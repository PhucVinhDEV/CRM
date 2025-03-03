package com.example.CRM.Customer.CustomerEAV.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = DateAttributeValue.DateAttributeValueEntity.TABLE_NAME)
public class DateAttributeValue extends AttributeValue {

    @Column(nullable = false)
    private LocalDate value;

    @UtilityClass
    static class DateAttributeValueEntity {
        public static final String TABLE_NAME = "J_DATEATTRIBUTEVALUE";
    }
}

package com.example.CRM.CustomerEAV.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.UtilityClass;

@Entity
@Table(name = Attribute.AttributeEntity.TABLE_NAME)
public class Attribute {
    @Id
    private Integer attributeId;

    @Column(name = AttributeEntity.NAME,unique = true, nullable = false)
    private String attributeName;

    @UtilityClass
    static class AttributeEntity{
        public static final String TABLE_NAME = "J_ATTRIBUTE";
        public static final String NAME = "J_NAME";
    }
}

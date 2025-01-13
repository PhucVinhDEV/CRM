package com.example.CRM.CustomerEAV.model;

import com.example.CRM.common.model.BaseEntity;
import com.example.CRM.common.util.JoinTableUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@Entity
@Getter
@Setter
@Table(name = StringAttributeValue.StringAttributeValueEntity.TABLE_NAME)
public class StringAttributeValue extends AttributeValue {

    @Column(nullable = false)
    private String value;

    @UtilityClass
    static class StringAttributeValueEntity {
        public static final String TABLE_NAME = "J_STRINGATTRIBUTEVALUE";
    }
}

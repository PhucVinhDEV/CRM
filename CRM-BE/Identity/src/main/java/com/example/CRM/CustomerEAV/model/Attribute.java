package com.example.CRM.CustomerEAV.model;

import com.example.CRM.CustomerEAV.util.TypeOfValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Attribute.AttributeEntity.TABLE_NAME)
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attributeId;

    @Column(name = AttributeEntity.NAME,unique = true, nullable = false)
    private String attributeName;

    @Enumerated(EnumType.STRING)
    @Column(name = AttributeEntity.TYPE, nullable = false)
    private TypeOfValue type; // Thêm trường type để chỉ định loại dữ liệu của Attribute

    @UtilityClass
    static class AttributeEntity {
        public static final String TABLE_NAME = "J_ATTRIBUTE";
        public static final String NAME = "J_NAME";
        public static final String TYPE = "J_TYPE"; // Tên cột cho type
    }

}

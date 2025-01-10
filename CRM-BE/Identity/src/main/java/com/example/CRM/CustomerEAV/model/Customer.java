package com.example.CRM.CustomerEAV.model;


import com.example.CRM.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = Customer.CustomerEntity.TABLE_NAME)
public class Customer extends BaseEntity {

    @Column(name = CustomerEntity.EMAIL, unique = true, nullable = false)
    private String email;
    @Column(name = CustomerEntity.FIRSTNAME, nullable = false)
    private String firstName;
    @Column(name = CustomerEntity.LASTNAME, nullable = false)
    private String lastName;
    @Column(name = CustomerEntity.PHONE, unique = true, nullable = false)
    private String phone;
    @Column(name = CustomerEntity.DAY_OF_BIRTH)
    private LocalDate dob;



    @UtilityClass
    static class CustomerEntity{
        public static final String TABLE_NAME = "J_CUSTOMER";
        public static final String EMAIL = "J_EMAIL";
        public static final String FIRSTNAME = "J_FIRSTNAME";
        public static final String LASTNAME = "J_LASTNAME";
        public static final String PHONE = "J_PHONE";
        public static final String DAY_OF_BIRTH = "J_DAY_OF_BIRTH";
    }
}

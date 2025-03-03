package com.example.CRM.Auth.Benifit.model;

import com.example.CRM.Customer.CustomerEAV.model.Customer;
import com.example.CRM.common.model.BaseEntityInt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import java.math.BigInteger;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = Benefit.BenefitEntity.TABLE_NAME)
public class Benefit extends BaseEntityInt {

    @Column(name = BenefitEntity.NAME, nullable = false)
    private String name;

    @Column(name = BenefitEntity.EMAIL, nullable = false)
    private Integer email;

    @Column(name = BenefitEntity.CUSTOMER, nullable = false)
    private Integer customer;

    @Column(name = BenefitEntity.PRICE, nullable = false)
    private BigInteger price;


    @UtilityClass
    static class BenefitEntity {
        public static final String TABLE_NAME = "J_BENEFIT";
        public static final String NAME = "J_NAME";
        public static final String EMAIL = "J_EMAIL";
        public static final String CUSTOMER = "J_CUSTOMER";
        public static final String PRICE = "J_PRICE";
    }
}

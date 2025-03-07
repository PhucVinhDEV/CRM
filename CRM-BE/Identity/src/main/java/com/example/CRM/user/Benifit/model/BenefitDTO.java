package com.example.CRM.user.Benifit.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class BenefitDTO {
    private String name;
    private Integer email;
    private Integer customer;
    private BigInteger price;
}

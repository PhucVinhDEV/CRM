package com.example.CRM.CustomerEAV.model;


import com.example.CRM.CustomerEAV.util.TypeOfValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class DateAttributeValue extends AttributeValue {

    @Column(nullable = false)
    private LocalDate value;


}

package com.example.CRM.CustomerEAV.model.DTO;

import com.example.CRM.CustomerEAV.util.TypeOfValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDTO {
    private Integer Id;
    private String attributeName;
    private TypeOfValue type;


}

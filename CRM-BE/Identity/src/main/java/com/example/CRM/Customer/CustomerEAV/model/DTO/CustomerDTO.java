package com.example.CRM.Customer.CustomerEAV.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private UUID id;              // ID từ BaseEntity
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime dob;
    private Map<String, String> attributes;
}

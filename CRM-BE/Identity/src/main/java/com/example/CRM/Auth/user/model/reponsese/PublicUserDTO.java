package com.example.CRM.Auth.user.model.reponsese;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;



@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicUserDTO {
    private UUID id;
    private String email;
    private LocalDate dayOfBirth;
    private UUID roleId;
    private String profilePicture;
    private String status;
    private LocalDateTime lastLogin;
    private String companyName;
    private String emailDomain;
    private String companyAddress;
    private String phoneNumber;
}

package com.example.CRM.user.user.model.reponsese;

import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.model.BenefitDTO;
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
    private String fullName;
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
    private BenefitDTO benefit;
}

package com.example.CRM.user.user.model.reponsese;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PrivateUserDTO {
    private UUID id;
    private String email;

    @JsonIgnore // Không trả về password trong API response
    private String password;

    private LocalDate dayOfBirth;
    private UUID roleId;
    private String profilePicture;
    private String status;
    private LocalDate lastLogin;
    private String companyName;
    private String emailDomain;
    private String companyAddress;
    private Integer phoneNumber;
    private String emailSignature;

    // SMTP Configuration (Chỉ dành cho Admin)
    private String smtpHost;
    private Integer smtpPort;
    private String smtpUsername;

    @JsonIgnore // Không trả về mật khẩu SMTP trong API response
    private String smtpPassword;
}

package com.example.CRM.user.user.model;

import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.common.model.BaseEntityUUID;
import com.example.CRM.common.util.JoinTableUtil;
import com.example.CRM.Auth.role.model.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = User.UserEntity.TABLE_NAME )
public class User extends BaseEntityUUID {

    @Column(name = UserEntity.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = UserEntity.PASSWORD, nullable = false)
    private String password;

    @Column(name = UserEntity.FULLNAME, nullable = false)
    private String fullName;

    @Column(name = UserEntity.PHONE)
    private String phone;

    @Column(name = UserEntity.AVATAR)
    private String avatar;

    @Column(name = UserEntity.COMPANY)
    private String company;

    @Column(name = UserEntity.EMAILDOMAIN)
    private String emailDomain;

    @Column(name = UserEntity.EMAILSIGNATURE)
    private String emailSignature;

    @Column(name = UserEntity.SMTP_HOST)
    private String smtpHost;

    @Column(name = UserEntity.SMTP_PORT)
    private String smtpPort;

    @Column(name = UserEntity.SMTP_USERNAME)
    private String smtpUsername;

    @Column(name = UserEntity.SMTP_PASSWORD)
    private String smtpPassword;

    @Column(name = UserEntity.USED_EMAIL)
    private Integer usedEmail;

    @Column(name = UserEntity.USED_CUSTOMER)
    private Integer usedCustomer;

    @Enumerated(EnumType.STRING) // Lưu dưới dạng chuỗi trong DB
    @Column(name = UserEntity.STATUS_VERIFIED, nullable = false)
    private StatusVerified statusVerified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = JoinTableUtil.USER_MAPPED_BY_ROLE, nullable = true) // Tên cột khóa ngoại trong bảng User
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = JoinTableUtil.USER_MAPPED_BY_BENEFIT, nullable = true) // Tên cột khóa ngoại trong bảng User
    private Benefit benefit;

    public User(String email,String fullName, String password,StatusVerified statusVerified, Role role, Benefit benefit) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.statusVerified = statusVerified;
        this.role = role;
        this.benefit = benefit;
    }


    public enum StatusVerified{
        PENDING,
        NONE,
        VERIDIED
    }



    @UtilityClass
    static class UserEntity {
        public static final String TABLE_NAME = "J_USER";
        public static final String EMAIL = "J_EMAIL";
        public static final String PASSWORD = "J_PASSWORD";
        public static final String FULLNAME = "J_FULLNAME";
        public static final String PHONE = "J_PHONE";
        public static final String COMPANY = "J_COMPANY";
        public static final String EMAILDOMAIN = "J_EMAILDOMAIN";
        public static final String EMAILSIGNATURE = "J_EMAILSIGNATURE";
        public static final String SMTP_HOST = "J_SMTP_HOST";
        public static final String SMTP_PORT = "J_SMTP_PORT";
        public static final String SMTP_USERNAME = "J_SMTP_USERNAME";
        public static final String SMTP_PASSWORD = "J_SMTP_PASSWORD";
        public static final String USED_EMAIL = "J_USED_EMAIL";
        public static final String USED_CUSTOMER = "J_USED_CUSTOMER";
        public static final String AVATAR = "J_AVATAR";
        public static final String STATUS_VERIFIED = "J_STATUS_VERIFIED";
    }
}

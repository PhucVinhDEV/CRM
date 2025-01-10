package com.example.CRM.CustomerEAV.model.record;

import com.example.CRM.common.validate.annotation.UUIDConstraint;
import com.example.CRM.common.validate.group.InsertInfo;
import com.example.CRM.common.validate.group.UpdateInfo;
import com.example.CRM.user.valiation.anomation.UniqueUser;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Validated
public record CustomerRecord (
        @UUIDConstraint(groups = {UpdateInfo.class})
        @Null( groups = InsertInfo.class , message = "ID must be null when creating a new entity")
        @NotNull(groups = UpdateInfo.class, message = "ID is required for updates")
        UUID id,                 // ID từ BaseEntity

        @Email(groups = {InsertInfo.class, UpdateInfo.class},message = "{user.email.invalid}")
        @NotBlank(groups = {InsertInfo.class, UpdateInfo.class},message = "{user.email.not-blank}")
        String email,

        String firstName,

        String lastName,

        @Size(groups = {InsertInfo.class, UpdateInfo.class},max = 15, message = "{user.phone.size}") // Example size constraint
        String phone,
        LocalDate dob,

        Map<String, Object> attributes
){}

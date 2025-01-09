package com.example.CRM.CustomerEAV.model.record;

import com.example.CRM.CustomerEAV.util.TypeOfValue;
import com.example.CRM.common.validate.group.InsertInfo;
import com.example.CRM.common.validate.group.UpdateInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.validation.annotation.Validated;

@Validated
public record AttributeRecord(

        @Null( groups = InsertInfo.class , message = "ID must be null when creating a new entity")
        @NotNull(groups = UpdateInfo.class, message = "ID is required for updates")
        Integer id,

        String attributeName,

        TypeOfValue type // Thêm type vào record
) {
}

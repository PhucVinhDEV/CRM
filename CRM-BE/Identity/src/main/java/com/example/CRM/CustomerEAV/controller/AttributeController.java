package com.example.CRM.CustomerEAV.controller;

import com.example.CRM.CustomerEAV.model.DTO.AttributeDTO;
import com.example.CRM.CustomerEAV.model.record.AttributeRecord;
import com.example.CRM.CustomerEAV.service.AttributeService;
import com.example.CRM.common.reponsese.ApiReponsese;
import com.example.CRM.common.util.DateTimeUtil;
import com.example.CRM.common.validate.group.InsertInfo;
import com.example.CRM.common.validate.group.UpdateInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/Attribute")
@Validated
public class AttributeController {
    private final AttributeService attributeService;

    @PostMapping
    public ApiReponsese<AttributeDTO> addAttribute(@Validated(InsertInfo.class) @RequestBody AttributeRecord record) {
        return ApiReponsese.<AttributeDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(attributeService.save(record))
                .build();
    }
    @PutMapping()
    public ApiReponsese<AttributeDTO> updateAttribute(
            @Validated(UpdateInfo.class) @RequestBody AttributeRecord record) {
        return ApiReponsese.<AttributeDTO>builder()
                .timestamp(DateTimeUtil.now())
                .result(attributeService.save(record))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiReponsese<Void> deleteAttribute(@PathVariable Integer id) {
        attributeService.delete(id);
        return ApiReponsese.<Void>builder()
                .timestamp(DateTimeUtil.now())
                .message("Attribute deleted successfully")
                .build();
    }
}

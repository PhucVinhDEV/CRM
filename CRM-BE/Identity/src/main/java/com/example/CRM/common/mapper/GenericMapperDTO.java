package com.example.CRM.common.mapper;

import com.example.CRM.common.model.BaseEntityInt;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface GenericMapperDTO <E extends BaseEntityInt,D>{
    D maptoDTO(E entity);
}

package com.example.CRM.permission.mapper;

import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.permission.model.Permission;
import com.example.CRM.permission.model.PermissionDTO;
import com.example.CRM.permission.model.PermissionRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends GenericMapper<PermissionRecord, Permission, PermissionDTO> {
}

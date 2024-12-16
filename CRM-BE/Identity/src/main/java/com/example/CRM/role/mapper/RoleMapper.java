package com.example.CRM.role.mapper;

import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.role.model.Role;
import com.example.CRM.role.model.RoleDTO;
import com.example.CRM.role.model.RoleRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends GenericMapper<RoleRecord, Role, RoleDTO> {
}

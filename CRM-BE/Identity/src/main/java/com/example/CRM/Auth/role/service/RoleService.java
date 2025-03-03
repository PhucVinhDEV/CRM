package com.example.CRM.Auth.role.service;

import com.example.CRM.Auth.role.mapper.RoleMapper;
import com.example.CRM.Auth.role.model.Role;
import com.example.CRM.Auth.role.model.RoleDTO;
import com.example.CRM.Auth.role.model.RoleRecord;
import com.example.CRM.Auth.role.repository.RoleRepository;
import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleService extends GenericService<Role, RoleRecord, RoleDTO, UUID> {
}

@AllArgsConstructor
class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public JpaRepository<Role, UUID> getRepository() {
        return roleRepository;
    }

    @Override
    public GenericMapper<RoleRecord, Role, RoleDTO> getMapper() {
        return roleMapper;
    }
}
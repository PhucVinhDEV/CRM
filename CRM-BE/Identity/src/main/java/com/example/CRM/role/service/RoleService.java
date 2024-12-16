package com.example.CRM.role.service;

import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.GenericService;
import com.example.CRM.role.mapper.RoleMapper;
import com.example.CRM.role.model.Role;
import com.example.CRM.role.model.RoleDTO;
import com.example.CRM.role.model.RoleRecord;
import com.example.CRM.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleService extends GenericService<Role , RoleRecord, RoleDTO, UUID> {
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
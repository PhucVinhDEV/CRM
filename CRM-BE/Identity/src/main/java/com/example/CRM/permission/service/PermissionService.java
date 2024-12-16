package com.example.CRM.permission.service;

import com.example.CRM.common.mapper.GenericMapper;
import com.example.CRM.common.service.GenericService;
import com.example.CRM.permission.mapper.PermissionMapper;
import com.example.CRM.permission.model.Permission;
import com.example.CRM.permission.model.PermissionDTO;
import com.example.CRM.permission.model.PermissionRecord;

import com.example.CRM.permission.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionService extends GenericService<Permission, PermissionRecord, PermissionDTO, UUID> {
}

@AllArgsConstructor
class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    @Override
    public JpaRepository<Permission, UUID> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<PermissionRecord, Permission, PermissionDTO> getMapper() {
        return mapper;
    }
}
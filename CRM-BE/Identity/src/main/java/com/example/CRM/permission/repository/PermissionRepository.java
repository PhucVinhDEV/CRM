package com.example.CRM.permission.repository;

import com.example.CRM.permission.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    boolean existsByName(String name);
}

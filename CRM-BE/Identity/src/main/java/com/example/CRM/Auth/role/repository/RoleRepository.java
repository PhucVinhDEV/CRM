package com.example.CRM.Auth.role.repository;

import com.example.CRM.Auth.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByRoleName(String name);


    Optional<Role> findByRoleName(String roleName);
}

package com.example.CRM.Auth.role.model;


import com.example.CRM.common.model.BaseEntityUUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = Role.RoleEntity.TABLE_NAME)
public class Role extends BaseEntityUUID {

    @Column(name = RoleEntity.NAME,nullable = false, unique = true)
    private String roleName;

    @Column(name = RoleEntity.DESCRIPTION,nullable = false)
    private String description;


    @UtilityClass
    static class RoleEntity {
        public static final String TABLE_NAME = "J_ROLE";
        public static final String NAME = "J_NAME";
        public static final String DESCRIPTION = "J_DESCRIPTION";
    }
}

package com.project.SEP_BE_EmployeeManagement.model.mapper;

import com.project.SEP_BE_EmployeeManagement.dto.RoleDto;
import com.project.SEP_BE_EmployeeManagement.model.Role;

public class RoleMapper {

    public static RoleDto toDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }

    public static Role toRole(RoleDto dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setRoleName(dto.getRoleName());
        return role;
    }
}

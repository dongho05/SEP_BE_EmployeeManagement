package com.project.SEP_BE_EmployeeManagement.dto;

import com.project.SEP_BE_EmployeeManagement.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private ERole roleName;
}

package com.project.SEP_BE_EmployeeManagement.dto;

import com.project.SEP_BE_EmployeeManagement.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    private Long id;
    private String name;
    private Date createdDate;
    private Date updatedDate;
    private Set<Role> role;
}

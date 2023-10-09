package com.project.SEP_BE_EmployeeManagement.model.mapper;

import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.model.Department;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {
    public static DepartmentDto toDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }

    public static List<DepartmentDto> toDtoList(List<Department> departmentList) {
        return departmentList.stream().map(DepartmentMapper::toDto).collect(Collectors.toList());
    }
}

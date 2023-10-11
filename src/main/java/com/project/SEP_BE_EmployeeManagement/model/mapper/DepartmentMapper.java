package com.project.SEP_BE_EmployeeManagement.model.mapper;

import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {
    public static DepartmentDto toDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCreatedDate(department.getCreatedDate());
        dto.setUpdatedDate(department.getUpdatedDate());
        return dto;
    }

    public static List<DepartmentDto> toDtoList(List<Department> departmentList) {
        return departmentList.stream().map(DepartmentMapper::toDto).collect(Collectors.toList());
    }

    public static Page<DepartmentDto> toDtoPage(Page<Department> departmentPage) {
        return new PageImpl<>(
                departmentPage.getContent().stream()
                        .map(DepartmentMapper::toDto)
                        .collect(Collectors.toList()),
                PageRequest.of(departmentPage.getPageable().getPageNumber(), departmentPage.getPageable().getPageSize()),
                departmentPage.getTotalElements()
        );
    }
}

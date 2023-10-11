package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.department.CreateDepartmentRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.department.DepartmentResponse;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {

    Page<DepartmentDto> getData(String search, Integer pageNo, Integer pageSize);

    DepartmentDto createDepartment(CreateDepartmentRequest request);

    DepartmentDto updateDearpartment(CreateDepartmentRequest request, long id) throws NotFoundException;

    int deleteDepartment(long id) throws NotFoundException;

}

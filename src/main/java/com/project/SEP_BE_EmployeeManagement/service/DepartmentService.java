package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.response.department.DepartmentResponse;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {

    DepartmentResponse getData(String search, Integer pageNo, Integer pageSize);
}

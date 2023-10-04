package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;
    @GetMapping("get-list")
    public List<Department> getList(){
        return departmentRepository.findAll();
    }
}

package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.department.CreateDepartmentRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.department.DepartmentResponse;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("get-list")
    public List<Department> getList() {
        return departmentRepository.findAll();
    }

    @GetMapping("/data")
    public ResponseEntity<DepartmentResponse> getDepartment(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                            @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                                            @RequestParam(name = "search", required = false, defaultValue = "") String search){
        DepartmentResponse response = departmentService.getData(search, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody CreateDepartmentRequest request){
        DepartmentDto response = departmentService.createDepartment(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

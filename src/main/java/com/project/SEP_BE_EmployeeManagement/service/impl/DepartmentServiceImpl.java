package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.department.CreateDepartmentRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.department.DepartmentResponse;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.DepartmentMapper;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.DepartmentService;
import javassist.NotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<DepartmentDto> getData(String search, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("createdDate").descending());
        Page<Department> page = departmentRepository.getDepartment(search,pageable);

        Page<DepartmentDto> response =  DepartmentMapper.toDtoPage(page);

        return response;
    }

    @Override
    public DepartmentDto getDepartmentById(long id) throws NotFoundException {
        DepartmentDto departmentDto = DepartmentMapper.toDto(departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with id: " + id + " Not Found"))) ;
        return departmentDto;
    }

    @Override
    public DepartmentDto createDepartment(CreateDepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setCreatedDate(Date.from(Instant.now()));
        departmentRepository.save(department);
        DepartmentDto dto = DepartmentMapper.toDto(department);
        return dto;
    }

    @Override
    public DepartmentDto updateDearpartment(CreateDepartmentRequest request, long id) throws NotFoundException {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with id: " + id + " Not Found"));
        department.setName(request.getName());
        department.setUpdatedDate(Date.from(Instant.now()));
        departmentRepository.save(department);
        DepartmentDto dto = DepartmentMapper.toDto(department);
        return dto;
    }

    @Override
    public int deleteDepartment(long id) throws NotFoundException {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with id: " + id + " Not Found"));
        List<User> userList = userRepository.getUserByDepartment(id);
        if(userList.size() == 0) {
            departmentRepository.delete(department);
            return 1;
        }
        return 0;
    }
}

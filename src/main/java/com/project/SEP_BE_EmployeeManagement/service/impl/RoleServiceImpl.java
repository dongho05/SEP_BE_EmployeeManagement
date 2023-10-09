package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.ERole;
import com.project.SEP_BE_EmployeeManagement.model.Role;
import com.project.SEP_BE_EmployeeManagement.repository.RoleRepository;
import com.project.SEP_BE_EmployeeManagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> getRole(String search, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return roleRepository.getRole(search, pageable);

    }
}

package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User login(LoginRequest request);
}

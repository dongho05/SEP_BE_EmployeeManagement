package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UserRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.user.UserResponse;
import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public User login(LoginRequest request);
//    public List<UserResponse> GetAllPerson();
    Optional<User> GetPersonByUsername(String username);
    User GetPersonByEmail(String email);
    public User CreateNewPerson(User user);
    public User UpdatePerson(UserRequest user);

    User createUser(CreateUser createUser);

    Page<User> getData( String departmentIdInput, String searchInput, String statusInput, Pageable pageable);

    public boolean UpdatePassword(String email,String newPassword);
}

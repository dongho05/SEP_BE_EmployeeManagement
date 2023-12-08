package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.ProfileRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UserRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.dto.response.user.UserResponse;
import com.project.SEP_BE_EmployeeManagement.model.User;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UpdateUserRequest;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public User login(LoginRequest request);
//    public List<UserResponse> GetAllPerson();
    Optional<User> GetPersonByUsername(String username);
    UserDto getUserById(long id) throws NotFoundException;

    UserDto updatePosition(long userId, long positionId) throws NotFoundException;
    UserDto updateUser(long id, UpdateUserRequest request) throws NotFoundException;
//    UserDto blockUser(long id) throws NotFoundException;
    MessageResponse changeStatus(long id) throws NotFoundException;
    UserDto updateProfile(ProfileRequest profileRequest, long id) throws NotFoundException;
    Optional<User> findByUsernameOrEmail(String email);

    User createUser(CreateUser createUser);

    Page<User> getData( String departmentIdInput, String searchInput, String statusInput, Pageable pageable);

//    public void UpdatePassword(String email,String newPassword);

    Boolean existsByEmail(String email);
    Double getDayOffByUserId(Long id);
}

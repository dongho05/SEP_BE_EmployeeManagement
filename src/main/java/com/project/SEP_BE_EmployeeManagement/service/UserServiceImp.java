package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UserRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.user.UserResponse;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.UserMapper;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User login(LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(),request.getPassword());
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public List<UserResponse> GetAllPerson() {
        List<UserResponse> response = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User u:
             list) {
            UserResponse ur = new UserResponse();
            ur.setAddress(u.getAddress());
            ur.setEmail(u.getEmail());
            ur.setPassword(u.getPassword());
            ur.setPhone(u.getPhone());
            ur.setUserImage(u.getUserImage());
            ur.setStatus(u.isStatus());
            ur.setBirthDay(u.getBirthDay());
            ur.setFullName(u.getFullName());
            ur.setGender(u.isGender());
            ur.setUsername(u.getUsername());
            ur.setEndWork(u.getEndWork());
            ur.setStartWork(u.getStartWork());
            response.add(ur);
        }
        return response;
    }

    @Override
    public Optional<User> GetPersonByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User GetPersonByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User CreateNewPerson(User user) {
        User u = userRepository.save(user);
        return u;
    }

    @Override
    public User UpdatePerson(UserRequest user) {
        User u = UserMapper.toUser(user);
        return null;
    }

    @Override
    public boolean UpdatePassword(String email,String newPassword) {
        User user = GetPersonByEmail(email);
        if(user != null){
            userRepository.updatePassword(email,newPassword);
        }
        return false;
    }
}

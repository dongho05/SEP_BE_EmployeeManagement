package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UserRequest;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.UserMapper;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/api/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest request){
        User user = userService.login(request);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("Tài khoản hoặc mật khẩu sai.");
    }
    @GetMapping("/api/get-all")
    public ResponseEntity<?> GetAllPerson(){
        return ResponseEntity.ok(userService.GetAllPerson());
    }
    @PostMapping("/api/create")
    public ResponseEntity<?> CreateNewUser(@RequestBody UserRequest user){
        User u = UserMapper.toUser(user);
        return ResponseEntity.ok(userService.CreateNewPerson(u));

    }
}

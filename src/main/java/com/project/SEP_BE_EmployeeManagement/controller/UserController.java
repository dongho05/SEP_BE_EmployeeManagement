package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "create")
    public MessageResponse createUser(@RequestBody CreateUser createUser) {

//            for(CreateUser s : createUser){
//                userService.createUser(s);
//            }
        userService.createUser(createUser);
        return new MessageResponse("Thêm người dùng thành công");

    }
}

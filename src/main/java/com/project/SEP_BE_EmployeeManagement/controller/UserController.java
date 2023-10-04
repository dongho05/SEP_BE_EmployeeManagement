package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.ProfileRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping(value = "data")
    public ResponseEntity<Page<User>> getData(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "30") int size,
                                              @RequestParam(name = "code", required = false) String code,
                                              @RequestParam(name = "departmentId", required = false) String departmentId,
                                              @RequestParam(name = "search", required = false) String search,
                                              @RequestParam(name = "status", required = false) String status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pageUsers = userService.getData(code, departmentId,search,status, pageable);
        return new ResponseEntity<>(pageUsers, HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDto> viewProfile(@PathVariable long id) throws NotFoundException {
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PatchMapping("/profile/{id}")
    public ResponseEntity<UserDto> updateProfile(@RequestBody ProfileRequest profileRequest, @PathVariable long id) throws NotFoundException {
        UserDto userDto = userService.updateProfile(profileRequest, id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}

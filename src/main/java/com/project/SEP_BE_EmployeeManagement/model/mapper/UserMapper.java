package com.project.SEP_BE_EmployeeManagement.model.mapper;

import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UserRequest;
import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class UserMapper {
    public static User toUser(UserRequest req) {
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        // Hash password using BCrypt
        String hash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12));
        user.setPassword(req.getPassword());
//        user.getCreatedDate(new Date());
        user.setStatus(1);
        user.setUserImage("no image");
        user.setUsername(req.getUsername());
        return user;
    }

    public static UserDto toUserDto( User user ){
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserCode(user.getUserCode());
        userDto.setStartWork(user.getStartWork());
        userDto.setEndWork(user.getEndWork());
        userDto.setUserImage(user.getUserImage());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAddress(user.getAddress());
        userDto.setPhone(user.getPhone());
        userDto.setStatus(user.getStatus());
        userDto.setBirthDay(user.getBirthDay());
        userDto.setGender(user.getGender());
        userDto.setContracts(user.getContracts());
        userDto.setPosition(user.getPosition());
        userDto.setDepartment(user.getDepartment());

        return userDto;
    }
}

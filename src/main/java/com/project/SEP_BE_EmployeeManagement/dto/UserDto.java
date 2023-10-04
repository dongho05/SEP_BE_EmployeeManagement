package com.project.SEP_BE_EmployeeManagement.dto;

import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String userCode;
    private LocalDate startWork;
    private LocalDate endWork;
    private String userImage;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Integer status;
    private LocalDate birthDay;
    private Integer gender;
    private Set<Contract> contracts;
    private Position position;
    private Department department;
}

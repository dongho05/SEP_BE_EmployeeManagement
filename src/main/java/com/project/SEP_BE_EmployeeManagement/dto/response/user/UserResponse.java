package com.project.SEP_BE_EmployeeManagement.dto.response.user;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class UserResponse {
    private long id;

    private String username;

    private LocalDate startWork;

    private LocalDate endWork;

    private String userImage;

    private String fullName;

    private String email;
    private String password;

    private String address;

    private String phone;

    private boolean status;

    private LocalDate birthDay;

    private boolean gender;
}

package com.project.SEP_BE_EmployeeManagement.dto.response.user;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
@Data
public class UserResponse {
    private String username;

    private Timestamp startDate;

    private Timestamp endDate;

    private String userImage;

    private String fullName;

    private String email;

    private String password;

    private String address;

    private String phone;

    private boolean status;

    private Timestamp createdAt;

    private Timestamp createdDate;

    private Timestamp birthDay;

    private boolean gender;
}

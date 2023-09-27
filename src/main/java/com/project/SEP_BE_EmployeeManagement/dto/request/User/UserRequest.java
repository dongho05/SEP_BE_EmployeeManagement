package com.project.SEP_BE_EmployeeManagement.dto.request.User;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.Set;

@Data
public class UserRequest {

    private String username;

    private String userImage;

    private String fullName;

    private String email;

    private String password;

    private boolean gender;

    private String phone;

    private boolean status;

    private Set<String> role;
}

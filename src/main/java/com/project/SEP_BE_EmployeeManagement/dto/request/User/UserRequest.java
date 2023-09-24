package com.project.SEP_BE_EmployeeManagement.dto.request.User;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
@Data
public class UserRequest {
    private String code;


    private String userImage;

    private String fullName;

    private String email;

    private String password;

    private String address;

    private String phone;

    private boolean status;


}

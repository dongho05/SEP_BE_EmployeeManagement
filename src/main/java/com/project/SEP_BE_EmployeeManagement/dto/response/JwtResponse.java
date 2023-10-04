package com.project.SEP_BE_EmployeeManagement.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String userCode;
    private Long departmentId;
    private String fullName;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, String userCode, Long departmentId, String fullName) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userCode = userCode;
        this.departmentId = departmentId;
        this.fullName = fullName;
    }
}

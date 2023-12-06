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
    private String departmentName;
    private String fullName;
    private String userImage;
    private Double dayoff;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, String userCode,
                       Long departmentId, String departmentName, String fullName,String userImage, Double dayoff) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userCode = userCode;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.fullName = fullName;
        this.userImage = userImage;
        this.dayoff = dayoff;
    }
}

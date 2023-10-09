package com.project.SEP_BE_EmployeeManagement.dto.request.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String fullName;
    private String userCode;
    private Integer gender;
    private String address;
    private String email;
    private String phone;
    private LocalDate birthDay;
    private long positionId;
    private long departmentId;
    private MultipartFile userImage;
    private MultipartFile contractFile;
}

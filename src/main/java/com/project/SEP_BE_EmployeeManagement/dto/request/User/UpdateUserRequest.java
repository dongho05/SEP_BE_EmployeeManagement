package com.project.SEP_BE_EmployeeManagement.dto.request.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.format.annotation.DateTimeFormat;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;
    private long positionId;
    private long departmentId;
    private MultipartFile userImage;
    @Required
    private MultipartFile contractFile;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startWork;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endWork;
}

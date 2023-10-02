package com.project.SEP_BE_EmployeeManagement.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CreateUser {
    @NotBlank(message = "Chưa nhập tên đăng nhập")
    @Size(min = 3, max = 50)
    private String username;

    @NotNull(message = "Chưa nhập mã nhân viên")
    private String userCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startWork;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endWork;

    private MultipartFile userImage;

    @NotBlank(message = "Chưa nhập tên")
    @Size(min = 6, max = 50)
    private String fullName;
    @NotBlank(message = "Chưa nhập email")
    @Size(min = 6, max = 50)
    private String email;

    private String password;

    private String address;
    @NotBlank(message = "Chưa nhập điện thoại")
    @Size(min = 6, max = 50)
    private String phone;

    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;
    @NotBlank(message = "Chưa chọn giới tính")
    private Integer gender;

    @NotBlank(message = "Chưa chọn vị trí")
    private long positionId;

    private long departmentId;

    private String contractName;

    private MultipartFile contractFile;
}

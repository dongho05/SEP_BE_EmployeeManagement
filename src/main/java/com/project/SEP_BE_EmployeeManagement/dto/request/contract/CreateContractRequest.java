package com.project.SEP_BE_EmployeeManagement.dto.request.contract;

import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivateKey;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateContractRequest {
    private String contractName;
    private MultipartFile contractFile;
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startWork;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endWork;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

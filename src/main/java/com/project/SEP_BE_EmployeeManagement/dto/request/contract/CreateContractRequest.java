package com.project.SEP_BE_EmployeeManagement.dto.request.contract;

import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivateKey;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateContractRequest {
    private String contractName;
    private MultipartFile contractFile;
//    private User userId;
    private Long userId;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

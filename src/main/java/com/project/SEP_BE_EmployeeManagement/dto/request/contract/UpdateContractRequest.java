package com.project.SEP_BE_EmployeeManagement.dto.request.contract;

import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateContractRequest {
    private String contractName;
    private MultipartFile contractFile;
}

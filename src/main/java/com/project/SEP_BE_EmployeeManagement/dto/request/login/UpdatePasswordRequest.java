package com.project.SEP_BE_EmployeeManagement.dto.request.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordReq {
    private String email;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;

}

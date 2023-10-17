package com.project.SEP_BE_EmployeeManagement.dto.request.login;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordReq {
    private String to;
    private String subject;
    private String body;
}

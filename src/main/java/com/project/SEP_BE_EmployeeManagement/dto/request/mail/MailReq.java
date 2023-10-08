package com.project.SEP_BE_EmployeeManagement.dto.request.mail;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailReq {
    private String to;
    private String[] cc;
    private String subject;
    private String body;
}

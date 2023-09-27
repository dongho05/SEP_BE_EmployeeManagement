package com.project.SEP_BE_EmployeeManagement.dto.request.mail;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@NoArgsConstructor
public class MailRequest {
    private String to;
    private String[] cc;
    private String subject;
    private String body;
}

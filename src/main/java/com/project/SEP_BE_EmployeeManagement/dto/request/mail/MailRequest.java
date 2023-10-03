package com.project.SEP_BE_EmployeeManagement.dto.request.mail;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private String to;
    private String[] cc;
    private String subject;
    private String body;
}

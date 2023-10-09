package com.project.SEP_BE_EmployeeManagement.service.mail;

import com.project.SEP_BE_EmployeeManagement.dto.request.login.ResetPasswordReq;
import com.project.SEP_BE_EmployeeManagement.dto.request.mail.MailReq;
import org.springframework.web.multipart.MultipartFile;

public interface MailService {
    String sendMail(MultipartFile[] file, MailReq request);
    String sendPassword( ResetPasswordReq request);
}

package com.project.SEP_BE_EmployeeManagement.service.mail;

import com.project.SEP_BE_EmployeeManagement.dto.request.login.ResetPasswordRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.mail.MailRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface MailService {
    String sendMail(MultipartFile[] file, MailRequest request);
    String sendPassword( ResetPasswordRequest request);
}

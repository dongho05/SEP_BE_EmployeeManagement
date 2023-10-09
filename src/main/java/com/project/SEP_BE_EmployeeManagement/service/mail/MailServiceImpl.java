package com.project.SEP_BE_EmployeeManagement.service.mail;

import com.project.SEP_BE_EmployeeManagement.dto.request.login.ResetPasswordReq;
import com.project.SEP_BE_EmployeeManagement.dto.request.mail.MailReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public String sendMail(MultipartFile[] file, MailReq request) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(request.getTo());
            mimeMessageHelper.setCc(request.getCc());
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.setText(request.getBody());

            for (int i = 0; i < file.length; i++) {
                mimeMessageHelper.addAttachment(
                        file[i].getOriginalFilename()
                        , new ByteArrayResource(file[i].getBytes())
                );
            }

            javaMailSender.send(mimeMessage);

            return "Mail sent!";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendPassword(ResetPasswordReq request) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(request.getTo());
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.setText(request.getBody());

            javaMailSender.send(mimeMessage);

            return "Mail sent!";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

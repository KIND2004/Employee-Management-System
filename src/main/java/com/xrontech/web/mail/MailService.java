package com.xrontech.web.mail;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.xrontech.web.dto.AccountCredentialMailDTO;
import com.xrontech.web.dto.ForgotPasswordMailDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSenderImpl mailSender;
    @Value("${spring.mail.username}")
    private String mailSenderUsername;

    public void sendAccountCredentialMail(String subject, String to, String name, String password) {
        try {
            File templateFile = ResourceUtils.getFile("classpath:user-credentials.hbs");
            String context = Files.readString(templateFile.toPath());

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compileInline(context);
            AccountCredentialMailDTO emailTemplateDTO = new AccountCredentialMailDTO(name, to, password);
            String html = template.apply(emailTemplateDTO);

            sendMail(subject, to, html);

        } catch (IOException e) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "IO_EXCEPTION", "IO Exception");
        }
    }

    public void sendForgotPasswordMail(String subject, String to, String name, String resetLink) {
        try {
            File templateFile = ResourceUtils.getFile("classpath:forgot-password.hbs");
            String context = Files.readString(templateFile.toPath());

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compileInline(context);

            ForgotPasswordMailDTO emailTemplateDTO = new ForgotPasswordMailDTO(name, resetLink);

            String html = template.apply(emailTemplateDTO);

            sendMail(subject, to, html);

        } catch (IOException e) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "IO_EXCEPTION", "IO Exception");
        }
    }

    private void sendMail(String subject, String to, String html) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailSenderUsername);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "EMAIL_SENDING_FAILED", "Email Sending Failed");
        }
    }
}

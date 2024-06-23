package com.xrontech.web.domain.mail;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
//import com.xrontech.spring.ecom.dto.EmailTemplateDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public void sendMail(String to, String subject, int code) {
        try {
            File templateFile = ResourceUtils.getFile("classpath:email-verification.hbs");
            String context = Files.readString(templateFile.toPath());

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compileInline(context);
            EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO(to, String.valueOf(code));
            String html = template.apply(emailTemplateDTO);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailSenderUsername);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

//            FileSystemResource file = new FileSystemResource(new File("path/to/pdf"));
//            helper.addAttachment("File Name",file);

            mailSender.send(mimeMessage);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

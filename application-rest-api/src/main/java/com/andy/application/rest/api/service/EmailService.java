package com.andy.application.rest.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void send(String to, UUID fileUuid){
        try{
            Path path = Paths.get(String.format("policies-%s",fileUuid));
            byte[] content = Files.readAllBytes(path);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.addAttachment(String.format("policies-%s",fileUuid), new ByteArrayResource(content));
            helper.setTo(to);
            helper.setSubject("Here are your Policy transactions for today");
            helper.setFrom("brokers@intact.net");
            helper.setText("Hello, attached to this email, are your Policy transactions for the day!", true);

            mailSender.send(mimeMessage);
        }catch(MessagingException | IOException e){
            System.out.printf("failed to send email %s", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}

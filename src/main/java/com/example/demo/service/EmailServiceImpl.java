package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {

    //to klasa serwisowa która wysyła emaile z potwierdzeniem, wywoływana przez inne serwisy a nie przez controller

    private static final String APP_URL = "localhost:8080";
    private static final String FROM = "projectexample6@gmail.com";

    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendConfirmationEmail(String to, String token) {
        String subject = "Register Confirmation";
        String textToSend = "To confirm your e-mail address, please click the link below:\n"
                + APP_URL + "/confirm?token=" + token;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(textToSend, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

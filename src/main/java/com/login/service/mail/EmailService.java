package com.login.service.mail;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetCode(String toEmail, String resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Şifre Sıfırlama Kodu");
        message.setText("Şifrenizi sıfırlamak için bu kodu kullanın: " + resetCode);
        mailSender.send(message);
    }
}

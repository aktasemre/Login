package com.login.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendResetCode(String toEmail, String resetCode) throws MessagingException {
        // MimeMessage oluştur
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        // Eposta detayları
        helper.setTo(toEmail);
        helper.setSubject("Şifre Sıfırlama Kodu");
        helper.setText("Şifrenizi sıfırlamak için bu kodu kullanın: " + resetCode, false); // HTML değil, düz metin gönderiyoruz
        helper.setFrom("randevudefteri@outlook.com");

        // Eposta gönderimi
        javaMailSender.send(mimeMessage);
    }

}

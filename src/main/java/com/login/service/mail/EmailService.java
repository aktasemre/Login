package com.login.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Async
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
        System.out.println("mailden once :"+resetCode);
        // Eposta detayları
        helper.setTo(toEmail);
        helper.setSubject("Şifre Sıfırlama Kodu");
        helper.setText("Şifrenizi sıfırlamak için bu kodu kullanın: " + resetCode, false); // HTML değil, düz metin gönderiyoruz
//<<<<<<< HEAD
        helper.setFrom("loginemrenes@outlook.com");
      //  helper.setFrom("randevudefteri@outlook.com");
        System.err.println("mail gonderilmeden hemen once :"+resetCode);
//=======
        helper.setFrom("loginakbil@outlook.com");

//>>>>>>> main
        // Eposta gönderimi
        javaMailSender.send(mimeMessage);
    }

}

package com.login.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Async
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Retryable ile 3 kez dene, 2 saniye aralıklarla
    @Retryable(value = MessagingException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendResetCode(String toEmail, String resetCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Şifre Sıfırlama Kodu");
        helper.setText("Şifrenizi sıfırlamak için bu kodu kullanın: " + resetCode, false);

        helper.setFrom("randevudefteri@outlook.com");

        javaMailSender.send(mimeMessage);
        System.out.println("E-posta gönderildi: " + resetCode);
    }
}

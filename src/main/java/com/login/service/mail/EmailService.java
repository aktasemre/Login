package com.login.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender; // Email göndermek için kullanılan JavaMailSender nesnesi
    private final TemplateEngine templateEngine; // HTML şablonları oluşturmak için Thymeleaf TemplateEngine

    // E-posta gönderen adresi, application.properties dosyasından alınır
    @Value("${spring.mail.username}")
    private String fromEmail;

    // Constructor Dependency Injection: Bağımlılıkları enjekte ediyoruz
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Async
    public void sendResetCode(String toEmail, String firstName, String lastName, String resetCode, LocalDateTime expiryTime) throws MessagingException {
        // MimeMessage oluşturuyoruz
        MimeMessage mimeMessage = createMimeMessage(toEmail, firstName, lastName, resetCode, expiryTime);

        // E-postayı gönderiyoruz
        javaMailSender.send(mimeMessage);

        // Başarılı gönderim sonrası loglama yapıyoruz
        System.out.println("E-posta gönderildi: " + resetCode);
    }


    private MimeMessage createMimeMessage(String toEmail, String firstName, String lastName, String resetCode, LocalDateTime expiryTime)
            throws MessagingException {
        // Yeni bir MimeMessage oluşturuyoruz
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // MimeMessageHelper, e-postaya HTML ve ek dosyalar eklememizi sağlar
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        // Thymeleaf şablonuna değişkenler ekliyoruz
        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        context.setVariable("resetCode", resetCode);
        // Tarihi formatlıyoruz
        String formattedExpiryTime = expiryTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        context.setVariable("expiryTime", formattedExpiryTime);


        // HTML içerikli e-posta şablonunu Thymeleaf ile oluşturuyoruz
        String htmlContent = templateEngine.process("email-template", context);

        // E-posta detaylarını ayarlıyoruz
        helper.setTo(toEmail); // Alıcı adresi
        helper.setSubject("Şifre Sıfırlama Kodu"); // E-posta konusu
        helper.setText(htmlContent, true); // E-posta içeriği (HTML formatı için true)

        // Gönderici adresi
        helper.setFrom(fromEmail);

        // Oluşturulan MimeMessage nesnesini döndürüyoruz
        return mimeMessage;
    }
}
